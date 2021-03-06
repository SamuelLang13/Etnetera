package com.etnetera.hr.data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

/**
 * Simple data entity describing basic properties of every JavaScript framework.
 * 
 * @author Etnetera
 *
 */
@Entity
public class JavaScriptFramework {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, length = 30)
	private String name;

	@ElementCollection
	private List <String> version = new ArrayList <>();

	@Column(nullable = false)
	private LocalDate deprecationDate;

	@Column(nullable = false)
	private Double hypeLevel;

	public JavaScriptFramework(String name, List<String> version, LocalDate deprecationDate, Double hypeLevel) {
		this.name = name;
		this.version = version;
		this.deprecationDate = deprecationDate;
		this.hypeLevel = hypeLevel;
	}

	public JavaScriptFramework(Long id, String name, List<String> version, LocalDate deprecationDate, Double hypeLevel) {
		this.id = id;
		this.name = name;
		this.version = version;
		this.deprecationDate = deprecationDate;
		this.hypeLevel = hypeLevel;
	}

	public JavaScriptFramework() {
	}

	public JavaScriptFramework(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getVersion() {
		return version;
	}

	public void setVersion(List<String> version) {
		this.version = version;
	}

	public LocalDate getDeprecationDate() {
		return deprecationDate;
	}

	public void setDeprecationDate(LocalDate deprecationDate) {
		this.deprecationDate = deprecationDate;
	}

	public Double getHypeLevel() {
		return hypeLevel;
	}

	public void setHypeLevel(Double hypeLevel) {
		this.hypeLevel = hypeLevel;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof JavaScriptFramework)) return false;
		JavaScriptFramework that = (JavaScriptFramework) o;
		return Objects.equals(name, that.name) && Objects.equals(version, that.version) && Objects.equals(deprecationDate, that.deprecationDate) && Objects.equals(hypeLevel, that.hypeLevel);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, version, deprecationDate, hypeLevel);
	}

	@Override
	public String toString() {
		return "JavaScriptFramework{" +
				"id=" + id +
				", name='" + name + '\'' +
				", version=" + version +
				", deprecationDate=" + deprecationDate +
				", hypeLevel=" + hypeLevel +
				'}';
	}
}
