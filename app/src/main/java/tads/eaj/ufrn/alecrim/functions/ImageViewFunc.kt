package tads.eaj.ufrn.alecrim.functions

import android.widget.ImageView
import coil.load
import tads.eaj.ufrn.alecrim.R

fun ImageView.carregarImagem (url:String? = null){
    load(url){
        error(R.drawable.erro)
        placeholder(R.drawable.placeholder)
    }
}