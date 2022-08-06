package tads.eaj.ufrn.alecrim.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import coil.load
import tads.eaj.ufrn.alecrim.R
import tads.eaj.ufrn.alecrim.databinding.ActivityFormularioProdutoBinding
import tads.eaj.ufrn.alecrim.dao.ProdutosDao
import tads.eaj.ufrn.alecrim.databinding.FormularioImagemBinding
import tads.eaj.ufrn.alecrim.functions.carregarImagem
import tads.eaj.ufrn.alecrim.model.Produto
import java.math.BigDecimal

class FormularioProdutoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormularioProdutoBinding.inflate(layoutInflater)
    }
    private var url : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraBotaoSalvar()
        binding.formularioProdutoTextinputnomeImageview.setOnClickListener{
            val bindingFormularioImagem =  FormularioImagemBinding.inflate(layoutInflater)
            bindingFormularioImagem.formularioImagemButton.setOnClickListener{
                val url = bindingFormularioImagem.activityFormularioImagemUrl.text.toString()
                bindingFormularioImagem.formularioImagemImageview.carregarImagem(url)
            }
            AlertDialog.Builder(this)
                .setNegativeButton("Cancelar") { _, _ -> }
                .setPositiveButton("Confirme") { _, _ ->
                    url = bindingFormularioImagem.activityFormularioImagemUrl.text.toString()
                    binding.formularioProdutoTextinputnomeImageview.carregarImagem(url)
                }
                .setView(bindingFormularioImagem.root)
                .show()
        }
    }

    private fun configuraBotaoSalvar() {
        val botaoSalvar = binding.activityFormularioProdutoBotaoSalvar
        val dao = ProdutosDao()
        botaoSalvar.setOnClickListener {
            val produtoNovo = criaProduto()
            dao.adiciona(produtoNovo)
            finish()
        }
    }

    private fun criaProduto(): Produto {
        val campoNome = binding.activityFormularioProdutoNome
        val nome = campoNome.text.toString()
        val campoDescricao = binding.activityFormularioProdutoDescricao
        val descricao = campoDescricao.text.toString()
        val campoValor = binding.activityFormularioProdutoValor
        val valorEmTexto = campoValor.text.toString()
        val valor = if (valorEmTexto.isBlank()) {
            BigDecimal.ZERO
        } else {
            BigDecimal(valorEmTexto)
        }

        return Produto(
            nome = nome,
            descricao = descricao,
            valor = valor,
            imagem = url
        )
    }

}