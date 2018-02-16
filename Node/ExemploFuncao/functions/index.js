'use strict';

const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

/**
 * Executa quando um usuário recebe uma mensagem e envia uma notificação
 *
 * As mensagens são adicionadas em  `/mensagens/{usuarioId}/{destinararioId}`.
 * O  token do dispositivo do usuário é salvo em `/usuarios/{usuarioId}/token`.
 */
exports.messageNotification = functions.database
    .instance('exemploautenticacao-c2c24')
    .ref('/mensagens/{destinatarioId}/{mensagemId}/{mensagem}')
    .onCreate((event) => {
        const usuarioId = event.data.child('origem').val();
        const destinatarioId = event.params.destinatarioId;
        const mensagemId = event.params.mensagemId;

        console.log(`O destinatario: ${destinatarioId}`);
        console.log(`O mensagem: ${mensagemId}`);
        console.log(`O usuario ${usuarioId}`);

        if(usuarioId === destinatarioId) {
            console.log('Retorno de mensagem');
            return 'no message';
        }

        // Obtem o tokem para notificar
        const getDeviceTokenPromise = admin.database()
            .ref(`/usuarios/${destinatarioId}/token`)
            .once('value');
        console.log(`query 1: /usuarios/${destinatarioId}/token`);

        // Obtem o email do usuário de origem
        const getUsuarioEmailPromise = admin.database()
            .ref(`/usuarios/${usuarioId}/email`)
            .once('value');
        console.log(`query 2: /usuarios/${usuarioId}/email`);

        return Promise.all([getDeviceTokenPromise, getUsuarioEmailPromise]).then((results) => {
            const token = results[0];
            const email = results[1];

            console.log(`O token: ${token.val()}`);
            console.log(`O email ${email.val()}`);

            // Verifica se existe algum tokem
            if (!token.exists()) {
                console.log('Sem Tokens de notificações a quem enviar.');
                return 'error no token';
            }

            // Detalhe da Notificação
            const payload = {
                notification: {
                    title: 'Tem mensagem para Você!',
                    body: `${email.val()} enviou mensagens à Você.`
                },
                data: {
                    usuarioId: `${usuarioId}`,
                    destinatarioId: `${destinatarioId}`,
                    remetente: `${email.val()}`
                }
            };

            // Manda a notificação
            return admin.messaging().sendToDevice(token.val(), payload);
        }).then(() => {
            console.log('Sucesso ao enviar notificação:');
            return 'ok';
        }).catch((error) => {
            console.error('Falha ao enviar notificação a', error);
        });
    });
