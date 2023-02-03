package com.QCA.API.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.QCA.API.jsonView.SolutionView;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "solution")
public class Solution {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(SolutionView.class)
	private Long id;

	@Column(name = "solution", columnDefinition = "LONGTEXT")
	@Lob
	@JsonView(SolutionView.class)
	private String solution;

	@Column(name = "created_at")
	@JsonView(SolutionView.class)
	private Date createdAt;

	@Column(name = "updated_at")
	@JsonView(SolutionView.class)
	private Date updatedAt;

	@Column(name = "up_votes")
	@JsonView(SolutionView.class)
	private int upVotes;

	@Column(name = "down_votes")
	@JsonView(SolutionView.class)
	private int downVotes;

	@Column(name = "is_correct")
	@JsonView(SolutionView.class)
	private boolean isCorrect;

	@ManyToOne
	@JoinColumn(name = "owner_id")
	@JsonView(SolutionView.class)
	private MyUser owner;

	@ManyToOne
	@JoinColumn(name = "question_id")
	@JsonView(SolutionView.class)
	private Question question;
	
	@OneToMany(mappedBy = "solution", fetch = FetchType.LAZY)
	
	private List<Comment> comments;
	
	@OneToMany(mappedBy = "solution", fetch = FetchType.LAZY)
	private List<Vote> votes;

	public Solution() {
	}

	public Solution(String solution, MyUser owner, Question question) {
		this.solution = solution;
		this.owner = owner;
		this.question = question;
		this.createdAt = new Date();
		this.updatedAt = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public int getUpVotes() {
		return upVotes;
	}

	public void setUpVotes(int upVotes) {
		this.upVotes = upVotes;
	}

	public int getDownVotes() {
		return downVotes;
	}

	public void setDownVotes(int downVotes) {
		this.downVotes = downVotes;
	}

	public boolean isCorrect() {
		return isCorrect;
	}

	public void setCorrect(boolean correct) {
		isCorrect = correct;
	}

	public MyUser getOwner() {
		return owner;
	}

	public void setOwner(MyUser owner) {
		this.owner = owner;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}
	
	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}
}
