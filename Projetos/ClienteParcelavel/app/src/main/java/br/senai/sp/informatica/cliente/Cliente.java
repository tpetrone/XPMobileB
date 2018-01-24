package br.senai.sp.informatica.cliente;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Cliente implements Parcelable {
    private String nomeFantasia;
    private String razaoSocial;
    private String cnpj;

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nomeFantasia);
        dest.writeString(this.razaoSocial);
        dest.writeString(this.cnpj);
    }

    public Cliente() {
    }

    protected Cliente(Parcel in) {
        this.nomeFantasia = in.readString();
        this.razaoSocial = in.readString();
        this.cnpj = in.readString();
    }

    public static final Parcelable.Creator<Cliente> CREATOR = new Parcelable.Creator<Cliente>() {
        @Override
        public Cliente createFromParcel(Parcel source) {
            return new Cliente(source);
        }

        @Override
        public Cliente[] newArray(int size) {
            return new Cliente[size];
        }
    };
}
