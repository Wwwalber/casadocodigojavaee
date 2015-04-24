package br.com.casadocodigo.managedbeans.admin;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.casadocodigo.daos.BookDAO;
import br.com.casadocodigo.infra.MessagesHelper;
import br.com.casadocodigo.models.Author;
import br.com.casadocodigo.models.Book;

@Model
public class AdminBooksBean {
	
	private Book product = new Book();
	@Inject
	private BookDAO productDAO;
	private List<String> selectedAuthorsIds = new ArrayList<>();
	@Inject
	private MessagesHelper messagesHelper;
	private List<Author> selectedAuthors = new ArrayList<>();

	@Transactional
	public String save(){
		populateBookAuthor();		
		productDAO.save(product);
		messagesHelper.addFlash(new FacesMessage("Livro gravado com sucesso"));
		clearObjects();
		return "/livros/list?faces-redirect=true";
	}
	
	public void setSelectedAuthors(List<Author> selectedAuthors) {
		this.selectedAuthors = selectedAuthors;
	}
	
	public List<Author> getSelectedAuthors() {
		return selectedAuthors;
	}

	private void clearObjects() {
		this.product = new Book();
		this.selectedAuthorsIds.clear();
	}

	private void populateBookAuthor() {
		selectedAuthorsIds.stream().map( (strId) -> {
			return new Author(Integer.parseInt(strId));
		}).forEach(product :: add);
	}
	
	public void setSelectedAuthorsIds(List<String> selectedAuthorsIds) {
		this.selectedAuthorsIds = selectedAuthorsIds;
	}
	
	public List<String> getSelectedAuthorsIds() {
		return selectedAuthorsIds;
	}
	
	public Book getProduct() {
		return product;
	}
	
}