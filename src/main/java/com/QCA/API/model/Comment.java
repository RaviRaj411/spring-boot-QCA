package com.QCA.API.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.QCA.API.jsonView.CommentView;
import com.fasterxml.jackson.annotation.JsonView;



@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(CommentView.class)
    private Long id;

    @Column(name = "comment")
    @Lob
    @JsonView(CommentView.class)
    private String comment;

    @Column(name = "created_at")
    @JsonView(CommentView.class)
    private Date createdAt;

    @Column(name = "updated_at")
    @JsonView(CommentView.class)
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonView(CommentView.class)
    private MyUser owner;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @JsonView(CommentView.class)
    private Comment parent;

    @ManyToOne
    @JoinColumn(name = "solution_id")
    @JsonView(CommentView.class)
    private Solution solution;
    
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<Comment> childComments;

    public Comment() {}

    public Comment(String comment, MyUser owner, Solution solution) {
        this.comment = comment;
        this.owner = owner;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public MyUser getOwner() {
        return owner;
    }

    public void setOwner(MyUser owner) {
        this.owner = owner;
    }

    public List<Comment> getChildComments() {
        return childComments;
    }

    public void setChildComments(List<Comment> childComments) {
        this.childComments = childComments;
    }

    public Comment getParent() {
        return parent;
    }

    public void setParent(Comment parent) {
        this.parent = parent;
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