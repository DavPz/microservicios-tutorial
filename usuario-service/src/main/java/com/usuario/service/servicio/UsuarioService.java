package com.usuario.service.servicio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.usuario.service.entidades.Usuario;
import com.usuario.service.feingclients.CarroFeignClient;
import com.usuario.service.feingclients.MotoFeignClient;
import com.usuario.service.modelos.Carro;
import com.usuario.service.modelos.Moto;
import com.usuario.service.repositorio.UsuarioReporitoy;

@Service
public class UsuarioService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private UsuarioReporitoy usuarioRepository;

	@Autowired
	private CarroFeignClient carroFeignClient;

	@Autowired
	private MotoFeignClient motoFeignClient;

	public List<Carro> getCarros(int usuarioId) {
		List<Carro> carros = restTemplate.getForObject("http://localhost:8002/carro/usuario/" + usuarioId, List.class);
		return carros;
	}

	public List<Moto> getMotos(int usuarioId) {
		List<Moto> motos = restTemplate.getForObject("http://localhost:8003/moto/usuario/" + usuarioId, List.class);
		return motos;
	}

	public Carro saveCarro(int usuarioId, Carro carro) {
		carro.setUsuarioId(usuarioId);
		Carro nuevoCarro = carroFeignClient.save(carro);
		return nuevoCarro;
	}

	public List<Carro> listarCarros(int usuarioId) {
		List<Carro> carros = carroFeignClient.getCarros(usuarioId);
		return carros;
	}

	public Moto saveMoto(int usuarioId, Moto moto) {
		moto.setUsuarioId(usuarioId);
		Moto nuevoMoto = motoFeignClient.save(moto);
		return nuevoMoto;
	}

	public List<Moto> listarMotos(int usuarioId) {
		List<Moto> motos = motoFeignClient.getMotos(usuarioId);
		return motos;
	}

	public List<Moto> getMotosFeign(int usuarioId) {
		List<Moto> motos = motoFeignClient.getMotos(usuarioId);
		return motos;
	}

	public List<Usuario> getAll() {
		return usuarioRepository.findAll();
	}

	public Usuario getUsuarioById(int id) {
		return usuarioRepository.findById(id).orElse(null);
	}

	public Usuario save(Usuario usuario) {
		Usuario nuevoUsuario = usuarioRepository.save(usuario);
		return nuevoUsuario;
	}

	public Map<String, Object> getUsuarioAndVehiculos(int usuarioId) {
		Map<String, Object> resultado = new HashMap<>();
		Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);

		if (usuario == null) {
			resultado.put("Mensaje", "Usuario no Existe");
			return resultado;
		}

		resultado.put("Usuario", usuario);

		List<Carro> carros = carroFeignClient.getCarros(usuarioId);

		if (carros == null || carros.isEmpty()) {
			resultado.put("Carros", "Usuario no posee Carros");
		} else {
			resultado.put("Carros", carros);
		}

		List<Moto> motos = motoFeignClient.getMotos(usuarioId);

		if (motos == null || motos.isEmpty()) {
			resultado.put("Motos", "Usuario no posee Motos");
		} else {
			resultado.put("Motos", motos);
		}

		return resultado;
	}

}
