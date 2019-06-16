import org.junit.jupiter.api.Test;
import prova.Boteco;
import prova.Produto;
import prova.ValidationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class test {

    // O valor em caixa na inicialização do Boteco deverá ser R$ 1.000,00 para fluxo de caixa
    @Test
    public void valorInicialoCaixaIgualMil(){
        //cenario
        double valorEsperado = 1000.00;


        //validacao
        Boteco boteco = new Boteco();

        //execucao
        assertEquals(valorEsperado, boteco.getCaixa());
    }

    // O estoque (quantidade) de um produto não pode ficar/ser negativo.
    // Na tentativa de uma entrada negativa, uma ValidationException deverá ser lançada.
    @Test
    public void estoqueNãoPodeSerNegativo(){
        //cenario
        Integer quantidade = -5;
        Produto produto = new Produto();

        //validacao

        Exception ex = assertThrows(ValidationException.class, () -> produto.cadastrar("Mussum", "Cacilds", 10, 10.00, 15.00, quantidade ));

        //execucao
        assertEquals(new ValidationException().getMessage(), ex.getMessage());
    }

    @Test
    public void estoqueNãoPodeFicarNegativo(){
        //cenario
        Integer quantidade = -5;
        Produto produto = new Produto();
        produto.cadastrar("Mussum", "Cacilds", 10, 10.00, 15.00, 4 );
        //validacao

        Exception ex = assertThrows(ValidationException.class, () -> produto.baixarDoEstoque(quantidade) );

        //execucao
        assertEquals(new ValidationException().getMessage(), ex.getMessage());
    }

    // O valor de venda de um produto não poderá ser menor do que o valor de compra.
    // Neste caso uma SecurityException deverá ser lançada,
    // com a mensagem "O valor de venda não deve ser menor do que o valor de compra"
    @Test
    public void valorVendaNãoPodeSerMenorValorCompraAoCadastrar(){
        //cenario
        double valorVenda = 5.00;
        double valorCompra = 10.00;
        Produto produto = new Produto();

        //validacao

        Exception ex = assertThrows(SecurityException.class, () -> produto.cadastrar("Mussum", "Cacilds", 10, valorCompra, valorVenda, 10 ));

        //execucao
        assertEquals("O valor de venda não deve ser menor do que o valor de compra", ex.getMessage());
    }

    // O valor de compra de um produto deverá ser maior do que 0.
    // Na tentativa de uma entrada negativa, uma ValidationException deverá ser lançada.
    @Test
    public void valorCompraProdutoNaoPodeSerMenorZero(){
        //cenário
        double valorCompra = -20.00;
        Produto produto = new Produto();

        //validacao
        Exception ex = assertThrows(SecurityException.class, () -> produto.cadastrar("Mussum", "Cacilds", 10, valorCompra, 10, 10 ));

        //execucao
        assertEquals(new ValidationException().getMessage(), ex.getMessage());
    }

    //O valor por litro precisa ser testado se está sendo calculado corretamente (de acordo com o valor de venda);
    @Test
    public void valorPorLitro(){
        //cenário
        double valorVenda = 20.00;
        Integer volume = 10;
        Produto produto = new Produto();


        //validacao
        produto.cadastrar("Mussum", "Cacilds", volume, 10.00, valorVenda, 10 );
        double valorPorLitro = valorVenda/volume;

        //execucao
        assertEquals(valorPorLitro, produto.getValorPorLitro());
    }

    //O campo "nome" do Produto deverá possuir entre 3 e 50 caracteres, inclusive.
    // Caso contrário deverá lançar uma exceção do tipo ValidationException,
    // com a mensagem "Número de caracteres inválido".
    @Test
    public void nomeDevePossuirMaisDe3Caracteres(){
        //cenário
        String nome = "Al";
        Produto produto = new Produto();


        //validacao
        Exception ex = assertThrows(ValidationException.class,
                () -> produto.setNome(nome));

        //execucao
        assertEquals("Número de caracteres inválido", ex.getMessage());
    }

    @Test
    public void nomeDevePossuirMenosDe50Caracteres(){
        //cenário
        String nome = "Almir Almir Almir Almir Almir Almir Almir Almir Almir Almir Almir";
        Produto produto = new Produto();


        //validacao
        Exception ex = assertThrows(ValidationException.class,
                () -> produto.setNome(nome));

        //execucao
        assertEquals("Número de caracteres inválido", ex.getMessage());
    }
}
