package com.netpace.iem.ahmed.model;

// Generated Jan 2, 2014 10:42:45 AM by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

/**
 * Category generated by hbm2java
 */
@Entity
@Table(name = "category", catalog = "iem")
public class Category implements java.io.Serializable {

	@Expose
	private Integer id;
	@Expose
	private String name;
	@Expose
	private String description;
	private Set<Income> incomes = new HashSet<Income>(0);
	private Set<Expense> expenses = new HashSet<Expense>(0);

	public Category() {
	}

	public Category(String name, String description, Set<Income> incomes,
			Set<Expense> expenses) {
		this.name = name;
		this.description = description;
		this.incomes = incomes;
		this.expenses = expenses;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name", length = 45)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "description", length = 45)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
	public Set<Income> getIncomes() {
		return this.incomes;
	}

	public void setIncomes(Set<Income> incomes) {
		this.incomes = incomes;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
	public Set<Expense> getExpenses() {
		return this.expenses;
	}

	public void setExpenses(Set<Expense> expenses) {
		this.expenses = expenses;
	}

}
