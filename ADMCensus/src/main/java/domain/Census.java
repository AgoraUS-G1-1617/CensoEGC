package domain;
import java.util.Date;
import java.util.HashMap;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@XmlRootElement(name = "census")
@Access(AccessType.PROPERTY)
public class Census extends DomainEntity {

	//Nos lo pasa creaci�n y administraci�n de votaciones
	// Id que identifica de forma �nia a la votaci�n
	private int idVotacion;
	
	//Versi�n de la votaci�n
	private int versionVotacion;
	
	// T�tulo de la votaci�n
	private String title;
	
	// Descripci�n de la votaci�n
	private String description;
	
	// Fecha en la que se inicia la votacion
	private Date startDate;
		
	// Fecha en la que finaliza la votaci�n
	private Date endDate;
	
	// Indica si el censo es "abierto" o "cerrado"
	private String tipo;
	
	//Indica el c�digo postal de la votaci�n
	private String postalCode;
	
	// Username del usuario que crea la votaci�n
	private String usernameCreator;

	//Lo trabajamos nosotros
	// Mapa encargado de asignar un true o false (ha votado o no) a un token  �nico de un usuario
	private HashMap<String, Boolean> votoPorUsuario = new HashMap<String, Boolean>();
	

	//Mirar qu� hacer con census y con questions
	
	public Census() {
		
	}
	
	@Column(unique = true)
	public int getIdVotacion() {
		return idVotacion;
	}
	

	public void setIdVotacion(int idVotacion) {
		this.idVotacion = idVotacion;
	}
	
	public int getVersionVotacion() {
		return versionVotacion;
	}
	

	public void setVersionVotacion(int versionVotacion) {
		this.versionVotacion = versionVotacion;
	}
	

	@NotBlank
	public String getTitle() {
		return title;
	}
	

	public void setTitle(String title) {
		this.title = title;
	}
	
	@NotBlank
	public String getDescription() {
		return description;
	}
	

	public void setDescription(String description) {
		this.description = description;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@NotNull
	public Date getStartDate() {
		return startDate;
	}
	

	public void setStartDate(Date startDate) {
		this.startDate=startDate;
	}
	

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@NotNull
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate=endDate;
	}
	
	@NotBlank
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	@NotBlank
	public String getPostalCode() {
		return postalCode;
	}
	

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	@NotBlank
	public String getUsernameCreator() {
		return usernameCreator;
	}
	

	public void setUsernameCreator(String usernameCreator) {
		this.usernameCreator = usernameCreator;
	}
	
	@MapKeyColumn(name = "token")
	@Column(name = "valor")
	@CollectionTable(name = "value", joinColumns = @JoinColumn(name = "token") )
	public HashMap<String, Boolean> getVotoPorUsuario() {
		return votoPorUsuario;
	}
	
	public void setVotoPorUsuario(HashMap<String, Boolean> votoPorUsuario) {
		this.votoPorUsuario = votoPorUsuario;
	}

}
