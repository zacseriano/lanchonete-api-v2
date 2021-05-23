package com.zacseriano.lanchoneteapi.resources;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.zacseriano.lanchoneteapi.exceptions.ClienteInexistenteException;
import com.zacseriano.lanchoneteapi.exceptions.PedidoInexistenteException;
import com.zacseriano.lanchoneteapi.exceptions.ProdutoInexistenteException;
import com.zacseriano.lanchoneteapi.exceptions.SemEstoqueException;
import com.zacseriano.lanchoneteapi.models.cliente.Cliente;
import com.zacseriano.lanchoneteapi.models.item.Item;
import com.zacseriano.lanchoneteapi.models.item.PrimeiroItemForm;
import com.zacseriano.lanchoneteapi.models.pedido.Pedido;
import com.zacseriano.lanchoneteapi.models.pedido.PedidoForm;
import com.zacseriano.lanchoneteapi.models.produto.Produto;
import com.zacseriano.lanchoneteapi.repositories.ClienteRepository;
import com.zacseriano.lanchoneteapi.repositories.ItemRepository;
import com.zacseriano.lanchoneteapi.repositories.PedidoRepository;
import com.zacseriano.lanchoneteapi.repositories.ProdutoRepository;
import com.zacseriano.lanchoneteapi.resources.dto.PedidoDto;
import com.zacseriano.lanchoneteapi.resources.dto.ProdutoDto;

import io.swagger.annotations.ApiOperation;

/**
 * SpringBoot RestController que implementa os end-points responsáveis pelo uso do Cliente na API
 */ 
@RestController
@RequestMapping(value="/cliente")
public class ClienteResource {
	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	PedidoRepository pedidoRepository;
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	@Autowired
	ItemRepository itemRepository;
	
	/**
	 * Método que lista todos os produtos para o cliente.
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
	@ApiOperation(value="Lista todos os produtos para o cliente.")
	@GetMapping("/solicitarPedido")
	public List<ProdutoDto> listaProdutos(){
		List<Produto> produtos = produtoRepository.findAll();
		return ProdutoDto.converter(produtos);
	}
	
	/**
	 * Método que lista os pedidos para o cliente.
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
	/*@ApiOperation(value="Lista os pedidos para o cliente.")
	@GetMapping("/consultarPedido")
	public List<Pedido> listaPedidos(){
		return pedidoRepository.findAll();
	}*/
	
	/**
	 * Método que lista um pedido para o cliente.Ocorre uma verificação, se o Pedido existe,
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
	 *//*
	@ApiOperation(value="Lista um pedido para o cliente.")
	@GetMapping("gerenciarPedido/{id}")
	public Pedido listaPedido(@PathVariable(value="id")long id){
		if (pedidoRepository.findById(id) == null) throw new PedidoInexistenteException();	
		return pedidoRepository.findById(id);
	}*/
	
	/**
	 * Método que cria um pedido vinculado a um cliente, e faz a seleção do primeiro item desse pedido, contendo Id do produto e a 
	 * quantidade desejada, colocando automaticamente o status desse pedido como "ANDAMENTO", os próximos itens serão salvos 
	 * no mesmo pedido por outro método, só que PUT. Ocorrem verificações se o produto existe, se tem estoque suficiente, se
	 * o cliente existe, gerando possíveis exceções.
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
	@ApiOperation(value="Cria o primeiro item do pedido selecionado pelo cliente.")
	@PostMapping("/solicitarPedido")
	@Transactional
	public ResponseEntity<PedidoDto> iniciarPedido(@RequestBody @Valid PrimeiroItemForm form, UriComponentsBuilder uriBuilder) {
		Pedido pedido = form.converter(produtoRepository, itemRepository, clienteRepository);
		pedidoRepository.save(pedido);
		
		URI uri = uriBuilder.path("/gerenciarPedido/{id}").buildAndExpand(pedido.getId()).toUri();
		return ResponseEntity.created(uri).body(new PedidoDto(pedido));
		
		
		/*
		Produto produto = produtoRepository.findById(itemForm.getProdutoId());
		
		if (produto == null) throw new ProdutoInexistenteException();		
		if (produto.verificarEstoque(itemForm.getQuantidade())) throw new SemEstoqueException();
		
		produto.diminuiEstoque(itemForm.getQuantidade());
		produtoRepository.save(produto);
			
		Item item = new Item(produto, itemForm.getQuantidade());
		item.setValorItem(item.defineValorItem(produto, item.getQuantidade()));
		itemRepository.save(item);
			
		Cliente cliente = clienteRepository.findByEmail(itemForm.getClienteEmail());
		if (cliente == null) throw new ClienteInexistenteException();
		
		Pedido pedido = new Pedido(cliente, item);
		item.setPedido(pedido);
			
		pedido.setValorTotal();
		pedido.setEstado("ANDAMENTO");
		return pedidoRepository.save(pedido);*/
				
	}
	
	/**
	 * Método que cria os itens seguintes de um pedido no estado "ANDAMENTO" vinculado a um cliente, e faz a seleção 
	 * deste outro item desse mesmo pedido, contendo Id do produto e a quantidade desejada. Por fim salva esse 
	 * item em uma nova entrada na lista de Itens de um Pedido. Ocorrem verificações: se algum pedido está
	 * no estado "ANDAMENTO", se o produto existe, se o estoque é suficiente, gerando possíveis exceções.
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
	/*@ApiOperation(value="Cria os itens seguintes do pedido em andamento solicitado pelo cliente.")
	@PutMapping("/solicitarPedido")
	public Pedido salvaOutroItem(@RequestBody PrimeiroItemForm itemForm) {
		
		Pedido pedido = pedidoRepository.findByEstado("ANDAMENTO");
		if (pedidoRepository.findByEstado("ANDAMENTO") == null) throw new PedidoInexistenteException();	
		
		Produto produto = produtoRepository.findById(itemForm.getProdutoId());
		if (produto == null) throw new ProdutoInexistenteException();		
		if (produto.verificarEstoque(itemForm.getQuantidade())) throw new SemEstoqueException();
		produto.diminuiEstoque(itemForm.getQuantidade());
		produtoRepository.save(produto);
		
		Item item = new Item(produto, itemForm.getQuantidade());
		item.setValorItem(item.defineValorItem(produto, item.getQuantidade()));
		itemRepository.save(item);
		
		pedido.addItem(item);
		pedido.setValorTotal();
		return pedidoRepository.save(pedido);	
	}*/	
	
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
	/*
	@ApiOperation(value="Cancela o pedido em andamento do cliente.")
	@PutMapping("/gerenciarPedido")
		public Pedido cancelarPedidoCliente(@RequestBody PedidoForm pedidoForm) {
		
			Pedido pedido = pedidoRepository.findById(pedidoForm.getPedidoId());
			if (pedido == null) throw new PedidoInexistenteException();	
			
			Produto produto = null;
			pedido.setEstado("CANCELADO");
			pedido.aumentaEstoque();
			
			for(int i=0; i<pedido.getItem().size(); i++) {
				produto = produtoRepository.findById(pedido.getItem().get(i).getProduto().getProdutoId());
				if (produtoRepository.findById(pedido.getItem().get(i).getProduto().getProdutoId()) == null) 
					throw new ProdutoInexistenteException();
				produtoRepository.saveAndFlush(produto);
			}			
			return pedidoRepository.saveAndFlush(pedido);
	}*/
}
