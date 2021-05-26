package com.zacseriano.lanchoneteapi.resources;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.zacseriano.lanchoneteapi.models.item.AdicionarItemForm;
import com.zacseriano.lanchoneteapi.models.item.PrimeiroItemForm;
import com.zacseriano.lanchoneteapi.models.pedido.CancelarPedidoForm;
import com.zacseriano.lanchoneteapi.models.pedido.ListaPedidoForm;
import com.zacseriano.lanchoneteapi.models.pedido.Pedido;
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
	// É PRA SER UM MÉTODO GET, SÓ QUE VOCE PASSA QUAL O SEU EMAIL, AINDA É NECESSÁRIO FAZER UM CONTROLE NESSA PARTE PRA QUE 
	//NEM TODOS OS CLIENTES CONSIGAM VER UM A LISTA DO OUTRO E ETC
	@ApiOperation(value="Lista todos os produtos para o cliente.")
	@PostMapping("/gerenciarPedido")
	public List<PedidoDto> listaPedidos(@RequestBody @Valid ListaPedidoForm form){
		List<Pedido> pedidos = clienteRepository.findByEmail(form.getEmail()).getPedido();
		return PedidoDto.converter(pedidos);
	}

	
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
	 */
	@ApiOperation(value="Lista um pedido para o cliente.")
	@GetMapping("/gerenciarPedido/{id}")
	public ResponseEntity<PedidoDto> listaProdutos(@PathVariable Long id) {

		Optional<Pedido> pedido = pedidoRepository.findById(id);
		if (pedido.isPresent()) {
			return ResponseEntity.ok(new PedidoDto(pedido.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
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
	@Cacheable(value = "listaProdutosCliente")
	public Page<ProdutoDto> listaProdutos(@PageableDefault(sort = "categoria", direction = Direction.ASC, page = 0, size = 20) Pageable paginacao){
		Page<Produto> produtos = produtoRepository.findAll(paginacao);
		return ProdutoDto.converter(produtos);
	}		
	
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
	@ApiOperation(value="Cria os itens seguintes do pedido em andamento solicitado pelo cliente.")
	@PutMapping("/solicitarPedido")
	@Transactional
	public ResponseEntity<PedidoDto> adicionaItem(@RequestBody @Valid AdicionarItemForm form, UriComponentsBuilder uriBuilder) {
		Pedido pedido = form.converter(produtoRepository, itemRepository, clienteRepository, pedidoRepository);
		
		URI uri = uriBuilder.path("/gerenciarPedido/{id}").buildAndExpand(pedido.getId()).toUri();
		return ResponseEntity.created(uri).body(new PedidoDto(pedido));
			
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
	
	@ApiOperation(value="Cancela o pedido em andamento do cliente.")
	@PutMapping("/gerenciarPedido")
	@Transactional
	public ResponseEntity<PedidoDto> cancelaPedido(@RequestBody @Valid CancelarPedidoForm form, UriComponentsBuilder uriBuilder) {
		Pedido pedido = form.converter(clienteRepository, pedidoRepository);
		
		URI uri = uriBuilder.path("/gerenciarPedido/{id}").buildAndExpand(pedido.getId()).toUri();
		return ResponseEntity.created(uri).body(new PedidoDto(pedido));
			
	}		
	
}
