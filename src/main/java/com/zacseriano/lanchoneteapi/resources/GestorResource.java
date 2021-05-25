package com.zacseriano.lanchoneteapi.resources;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
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

import com.zacseriano.lanchoneteapi.exceptions.produto.ProdutoExistenteException;
import com.zacseriano.lanchoneteapi.exceptions.produto.ProdutoInexistenteException;
import com.zacseriano.lanchoneteapi.models.pedido.AtualizacaoPedidoForm;
import com.zacseriano.lanchoneteapi.models.pedido.Pedido;
import com.zacseriano.lanchoneteapi.models.produto.AtualizacaoProdutoForm;
import com.zacseriano.lanchoneteapi.models.produto.Produto;
import com.zacseriano.lanchoneteapi.repositories.ClienteRepository;
import com.zacseriano.lanchoneteapi.repositories.GestorRepository;
import com.zacseriano.lanchoneteapi.repositories.PedidoRepository;
import com.zacseriano.lanchoneteapi.repositories.ProdutoRepository;
import com.zacseriano.lanchoneteapi.resources.dto.PedidoDtoGestor;
import com.zacseriano.lanchoneteapi.resources.dto.ProdutoDtoGestor;

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
	public List<ProdutoDtoGestor> listaProdutos(){
		List<Produto> produtos = produtoRepository.findAll();
		return ProdutoDtoGestor.converter(produtos);
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
	public ResponseEntity<ProdutoDtoGestor> listaProdutos(@PathVariable Long id) {

		Optional<Produto> produto = produtoRepository.findById(id);
		if (produto.isPresent()) {
			return ResponseEntity.ok(new ProdutoDtoGestor(produto.get()));
		}
		
		return ResponseEntity.notFound().build();
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
	@Transactional
	public ResponseEntity<ProdutoDtoGestor> cadastraProduto(@Valid @RequestBody Produto produto) {

		Produto consultaProduto = produtoRepository.findByNome(produto.getNome());
		if (consultaProduto != null) throw new ProdutoExistenteException();			
		produtoRepository.save(produto);
		
		URI uri = UriComponentsBuilder.newInstance()
			      .path("/gestor/gerenciarProduto/").query("{id}").buildAndExpand(produto.getProdutoId()).toUri();
		
		return ResponseEntity.created(uri).body(new ProdutoDtoGestor(produto));		
		
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
	@Transactional
	public void deletaProduto(@Valid @RequestBody long produtoId) {
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
	@PutMapping("/gerenciarProduto/{id}")
	@Transactional
	public ResponseEntity<ProdutoDtoGestor> atualizar(@PathVariable Long id, @Valid @RequestBody AtualizacaoProdutoForm form) {
		Optional<Produto> optional = produtoRepository.findById(id);
		if (optional.isPresent()) {
			Produto produto = form.atualizar(id, produtoRepository);
			return ResponseEntity.ok(new ProdutoDtoGestor(produto));
			
		}
		
		return ResponseEntity.notFound().build();
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
	public List<PedidoDtoGestor> listaPedidos(){
		List<Pedido> pedidos = pedidoRepository.findAll();
		return PedidoDtoGestor.converter(pedidos);
	}
	
	@ApiOperation(value="Lista um pedido para o gestor.")
	@GetMapping("/gerenciarPedido/{id}")
	public ResponseEntity<PedidoDtoGestor> listaPedido(@PathVariable Long id) {

		Optional<Pedido> pedido = pedidoRepository.findById(id);
		if (pedido.isPresent()) {
			return ResponseEntity.ok(new PedidoDtoGestor(pedido.get()));
		}
		
		return ResponseEntity.notFound().build();
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
	@PutMapping("/gerenciarPedido/{id}")
	@Transactional
	public ResponseEntity<PedidoDtoGestor> atualizaEstado(@PathVariable Long id, @Valid @RequestBody AtualizacaoPedidoForm form) {
		
		Optional<Pedido> optional = pedidoRepository.findById(id);
		if (optional.isPresent()) {
			Pedido pedido = form.atualizar(id, pedidoRepository);
			return ResponseEntity.ok(new PedidoDtoGestor(pedido));
			
		}
		
		return ResponseEntity.notFound().build();
	}	
}