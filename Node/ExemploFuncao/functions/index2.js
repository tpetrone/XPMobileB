'use strict';

 const functions = require('firebase-functions');
 const admin = require('firebase-admin');
 admin.initializeApp(functions.config().firebase);

 exports.messageNotification = functions.database.instance('exemploautenticacao-c2c24')
    .ref('/mensagens/{destinatarioId}/{mensagemId}/{mensagem}').onCreate((event) => {
        const usuarioId = event.data.child('origem').val();
        const destinatarioId = event.params.destinatarioId;
        if(usuarioId === destinatarioId) { return 'no message'; }
        const getDeviceTokenPromise = admin.database().ref(`/usuarios/${destinatarioId}/token`).once('value');
        const getUsuarioEmailPromise = admin.database().ref(`/usuarios/${usuarioId}/email`).once('value');
        return Promise.all([getDeviceTokenPromise, getUsuarioEmailPromise]).then((results) => {
            const token = results[0];
            const email = results[1];
            if (!token.exists()) { return 'error no token'; }
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
            return admin.messaging().sendToDevice(token.val(), payload);
        }).then(() => { return 'ok';
        }).catch((error) => { console.error('Falha ao enviar notificação a', error);
        });
    });

