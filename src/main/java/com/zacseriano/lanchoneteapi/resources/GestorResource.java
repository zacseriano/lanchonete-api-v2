package com.zacseriano.lanchoneteapi.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.zacseriano.lanchoneteapi.exceptions.PedidoInexistenteException;
import com.zacseriano.lanchoneteapi.exceptions.ProdutoExistenteException;
import com.zacseriano.lanchoneteapi.exceptions.ProdutoInexistenteException;
import com.zacseriano.lanchoneteapi.models.pedido.Pedido;
import com.zacseriano.lanchoneteapi.models.pedido.PedidoForm;
import com.zacseriano.lanchoneteapi.models.produto.Produto;
import com.zacseriano.lanchoneteapi.repositories.ClienteRepository;
import com.zacseriano.lanchoneteapi.repositories.GestorRepository;
import com.zacseriano.lanchoneteapi.repositories.PedidoRepository;
import com.zacseriano.lanchoneteapi.repositories.ProdutoRepository;

import io.swagger.annotations.ApiOperation;

/**
 * SpringBoot RestController que implementa os end-points responsáveis pelo uso do Gestor na API.
 */ 
@RestController
@RequestMapping(value="/gestor")
public class GestorResource {
	@Autowired
	GestorRepository gestorRepository;	
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	PedidoRepository pedidoRepository;
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	/**
	 * Método que lista todos os produtos para o Gestor.
	 * 
	 * HTTP Status:
	 * 
	 * 200 - OK: Tudo ocorreu como esperado
	 * 400 - Bad Request: A requisição não foi aceita, algum campo está faltando
	 * 401 - Unauthorized: Chave da API inválida
	 * 403 - Forbidden: A chave da API não tem permissão para fazer a requisição
	 * 404 - Not Found: O recurso requisitado não existe
	 * 500, 502, 503, 504 - Erros de server: problemas na Java API
	 */
	@ApiOperation(value="Lista todos os produtos para o gestor.")
	@GetMapping("/gerenciarProduto")
	public List<Produto> listaProdutos(){
		return produtoRepository.findAll();
	}
	
	/**
	 * Método que lista um pedido, identificado por Id, para o Gestor.
	 * 
	 * HTTP Status:
	 * 
	 * 200 - OK: Tudo ocorreu como esperado
	 * 400 - Bad Request: A requisição não foi aceita, algum campo está faltando
	 * 401 - Unauthorized: Chave da API inválida
	 * 403 - Forbidden: A chave da API não tem permissão para fazer a requisição
	 * 404 - Not Found: O recurso requisitado não existe
	 * 500, 502, 503, 504 - Erros de server: problemas na Java API
	 */
	@ApiOperation(value="Lista um produto, rastreado pelo id, para o gestor.")
	@GetMapping("/gerenciarProduto/{id}")
	public Produto listaProduto(@PathVariable(value="id")long id){
		if (produtoRepository.findById(id) == null) throw new ProdutoInexistenteException();	
		return produtoRepository.findById(id);
	}
	
	/**
	 * Método que salva um produto no banco de dados. Ocorre a verificação se o Produto já existe com o mesmo nome
	 * , gerando uma possível exceção.
	 * 
	 * HTTP Status:
	 * 
	 * 200 - OK: Tudo ocorreu como esperado
	 * 400 - Bad Request: A requisição não foi aceita, algum campo está faltando
	 * 401 - Unauthorized: Chave da API inválida
	 * 403 - Forbidden: A chave da API não tem permissão para fazer a requisição
	 * 404 - Not Found: O recurso requisitado não existe
	 * 500, 502, 503, 504 - Erros de server: problemas na Java API
	 */
	@ApiOperation(value="Salva um produto único no banco de dados.")
	@PostMapping("/gerenciarProduto")
	public ResponseEntity<Produto> cadastraProduto(@RequestBody @Valid Produto produto) {
		
		Produto consultaProduto = produtoRepository.findByNome(produto.getNome());
		if (consultaProduto != null) throw new ProdutoExistenteException();			
		produtoRepository.save(produto);
		
		URI uri = UriComponentsBuilder.newInstance()
			      .path("/gestor/gerenciarProduto/").query("{id}").buildAndExpand(produto.getProdutoId()).toUri();
		
		return ResponseEntity.created(uri).body(produto);		
		
	}
	
	/**
	 * Método que deleta um produto no banco de dados. Ocorre a verificação se o Produto existe, gerando uma possível exceção.
	 * 
	 * HTTP Status:
	 * 
	 * 200 - OK: Tudo ocorreu como esperado
	 * 400 - Bad Request: A requisição não foi aceita, algum campo está faltando
	 * 401 - Unauthorized: Chave da API inválida
	 * 403 - Forbidden: A chave da API não tem permissão para fazer a requisição
	 * 404 - Not Found: O recurso requisitado não existe
	 * 500, 502, 503, 504 - Erros de server: problemas na Java API
	 */
	@ApiOperation(value="Deleta um produto, encontrado pelo seu Id.")
	@DeleteMapping("/gerenciarProduto")
	public void deletaProduto(@RequestBody @Valid long produtoId) {
		if (produtoRepository.findById(produtoId) == null) throw new ProdutoInexistenteException();
		produtoRepository.delete(produtoRepository.findById(produtoId));
	}
	
	/**
	 * Método que atualiza um produto no banco de dados. Ocorre a verificação se o Produto existe, gerando uma possível exceção.
	 * 
	 * HTTP Status:
	 * 
	 * 200 - OK: Tudo ocorreu como esperado
	 * 400 - Bad Request: A requisição não foi aceita, algum campo está faltando
	 * 401 - Unauthorized: Chave da API inválida
	 * 403 - Forbidden: A chave da API não tem permissão para fazer a requisição
	 * 404 - Not Found: O recurso requisitado não existe
	 * 500, 502, 503, 504 - Erros de server: problemas na Java API
	 */
	@ApiOperation(value="Atualiza um produto.")
	@PutMapping("/gerenciarProduto")
	public Produto atualizaProduto(@RequestBody @Valid Produto produto) {
		if (produto == null) throw new ProdutoInexistenteException();
		return produtoRepository.save(produto);
	}
	
	/**
	 * Método que lista todos os pedidos existentes no repositório de Pedidos da API
	 * 
	 * HTTP Status:
	 * 
	 * 200 - OK: Tudo ocorreu como esperado
	 * 400 - Bad Request: A requisição não foi aceita, algum campo está faltando
	 * 401 - Unauthorized: Chave da API inválida
	 * 403 - Forbidden: A chave da API não tem permissão para fazer a requisição
	 * 404 - Not Found: O recurso requisitado não existe
	 * 500, 502, 503, 504 - Erros de server: problemas na Java API
	 */
	@ApiOperation(value="Lista todos os pedidos existentes para o gestor.")
	@GetMapping("/gerenciarPedido")
	public List<Pedido> listaPedidos(){
		return pedidoRepository.findAll();
	}
	
	/**
	 * Método que recebe um ID de pedido e um estado, atualiza o pedido vinculado a esse ID com o novo estado,
	 * ocorre uma verificação se o Produto existe, gerando uma possível possível exceção.
	 * 
	 * HTTP Status:
	 * 
	 * 200 - OK: Tudo ocorreu como esperado
	 * 400 - Bad Request: A requisição não foi aceita, algum campo está faltando
	 * 401 - Unauthorized: Chave da API inválida
	 * 403 - Forbidden: A chave da API não tem permissão para fazer a requisição
	 * 404 - Not Found: O recurso requisitado não existe
	 * 500, 502, 503, 504 - Erros de server: problemas na Java API
	 */
	@ApiOperation(value="Atualiza o estado de um pedido.")
	@PutMapping("/gerenciarPedido")
	public Pedido atualizaEstado(@RequestBody @Valid PedidoForm pedidoForm) {
		
		Pedido pedido = pedidoRepository.findById(pedidoForm.getPedidoId());
		if (pedido == null) throw new PedidoInexistenteException();	
		
		
		return pedidoRepository.saveAndFlush(pedido);
	}
	
	/**
	 * Método que recebe o ID de um pedido e atualiza o status do mesmo para "CANCELADO", por fim, atualiza
	 * os novos valores do estoque de cada produto, que é ressarcido. Ocorre uma verificação, se o Pedido existe,
	 * gerando uma possível possível exceção.
	 * 
	 * HTTP Status:
	 * 
	 * 200 - OK: Tudo ocorreu como esperado
	 * 400 - Bad Request: A requisição não foi aceita, algum campo está faltando
	 * 401 - Unauthorized: Chave da API inválida
	 * 403 - Forbidden: A chave da API não tem permissão para fazer a requisição
	 * 404 - Not Found: O recurso requisitado não existe
	 * 500, 502, 503, 504 - Erros de server: problemas na Java API
	 */
	@ApiOperation(value="Cancela um pedido.")
	@PutMapping("/cancelarPedido")
	public Pedido cancelarPedido(@RequestBody PedidoForm pedidoForm) {
		
		Pedido pedido = pedidoRepository.findById(pedidoForm.getPedidoId());
		if (pedido == null) throw new PedidoInexistenteException();	
		
		Produto produto = new Produto();
		
		for(int i=0; i<pedido.getItem().size(); i++) {
			produto = produtoRepository.findById(pedido.getItem().get(i).getProduto().getProdutoId());
			produtoRepository.saveAndFlush(produto);
		}		
		return pedidoRepository.save(pedido);				
	}	
}