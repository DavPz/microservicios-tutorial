package com.usuario.service.controlador;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usuario.service.entidades.Usuario;
import com.usuario.service.modelos.Carro;
import com.usuario.service.modelos.Moto;
import com.usuario.service.servicio.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping
	public ResponseEntity<List<Usuario>> listarUsuarios() {
		List<Usuario> usuarios = usuarioService.getAll();
		if (usuarios.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(usuarios);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Usuario> obtenerUsuario(@PathVariable("id") int id) {
		Usuario usuario = usuarioService.getUsuarioById(id);
		if (usuario == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(usuario);
		}

	}

	@PostMapping
	public ResponseEntity<Usuario> guardarUsuario(@RequestBody Usuario usuario) {
		Usuario nuevoUsuario = usuarioService.save(usuario);
		return ResponseEntity.ok(nuevoUsuario);
	}

	@GetMapping("/carros/{usuarioId}")
	public ResponseEntity<List<Carro>> getCarros(@PathVariable("usuarioId") int id) {
		Usuario usuario = usuarioService.getUsuarioById(id);
		if (usuario == null) {
			return ResponseEntity.notFound().build();
		}

		List<Carro> carros = usuarioService.getCarros(id);

		return ResponseEntity.ok(carros);
	}

	@GetMapping("/motos/{usuarioId}")
	public ResponseEntity<List<Moto>> getMotos(@PathVariable("usuarioId") int id) {
		Usuario usuario = usuarioService.getUsuarioById(id);
		if (usuario == null) {
			return ResponseEntity.notFound().build();
		}

		List<Moto> motos = usuarioService.getMotos(id);

		return ResponseEntity.ok(motos);
	}

	@PostMapping("/carro/{usuarioId}")
	public ResponseEntity<Carro> guardarCarro(@PathVariable("usuarioId") int id, @RequestBody Carro carro) {
		Carro nuevoCarro = usuarioService.saveCarro(id, carro);
		return ResponseEntity.ok(nuevoCarro);
	}
	
	@PostMapping("/moto/{usuarioId}")
	public ResponseEntity<Moto> guardarMoto(@PathVariable("usuarioId") int id, @RequestBody Moto carro) {
		Moto nuevaMoto = usuarioService.saveMoto(id, carro);
		return ResponseEntity.ok(nuevaMoto);
	}
	
	@GetMapping("/listarCarros/{usuarioId}")
	public ResponseEntity<List<Carro>> listarCarros(@PathVariable("usuarioId") int id) {
		List<Carro> carros = usuarioService.listarCarros(id);
		return ResponseEntity.ok(carros);
	}
	
	@GetMapping("/listarMotos/{usuarioId}")
	public ResponseEntity<List<Moto>> listarMotos(@PathVariable("usuarioId") int id) {
		List<Moto> motos = usuarioService.listarMotos(id);
		return ResponseEntity.ok(motos);
	}
	
	@GetMapping("/listarTodo/{usuarioId}")
	public ResponseEntity<Map<String, Object> > listarTodo(@PathVariable("usuarioId") int id) {
		Map<String, Object> resultado = usuarioService.getUsuarioAndVehiculos(id);
		return ResponseEntity.ok(resultado);
	}
	
	
	
	

}
