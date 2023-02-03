package com.QCA.API.model;

import java.util.Date;
import javax.persistence.*;

import com.QCA.API.jsonView.VoteView;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "vote")
public class Vote {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(VoteView.class)
	private Long id;

	@Column(name = "vote_type", nullable = true)
	@JsonView(VoteView.class)
	private Boolean voteType;

	@Column(name = "created_at")
	@JsonView(VoteView.class)
	private Date createdAt;

	@Column(name = "updated_at")
	@JsonView(VoteView.class)
	private Date updatedAt;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonView(VoteView.class)
	private MyUser user;

	@ManyToOne
	@JoinColumn(name = "solution_id")
	@JsonView(VoteView.class)
	private Solution solution;

	public Vote() {
	}

	public Vote(Boolean voteType, MyUser user, Solution solution) {
		this.voteType = voteType;
		this.user = user;
		this.solution = solution;
		this.createdAt = new Date();
		this.updatedAt = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getVoteType() {
		return voteType;
	}

	public void setVoteType(Boolean voteType) {
		this.voteType = voteType;
		this.updatedAt = new Date();
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

	public MyUser getUser() {
		return user;
	}

	public void setUser(MyUser user) {
		this.user = user;
	}

	public Solution getSolution() {
		return solution;
	}

	public void setSolution(Solution solution) {
		this.solution = solution;
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