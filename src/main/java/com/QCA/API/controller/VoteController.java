package com.QCA.API.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.QCA.API.jsonView.VoteView;
import com.QCA.API.model.MyUser;
import com.QCA.API.model.Solution;
import com.QCA.API.model.Vote;
import com.QCA.API.repository.SolutionRepository;
import com.QCA.API.repository.UserRepository;
import com.QCA.API.repository.VoteRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class VoteController {
	@Autowired
	public UserRepository ur;
	@Autowired
	public VoteRepository vr;
	@Autowired
	public SolutionRepository sr;
	@Autowired
	private ObjectMapper objectMapper;
	@GetMapping("votes/{solution_id}")
	public ResponseEntity<String> getVote(@PathVariable long solution_id) throws JsonProcessingException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object principal = auth.getPrincipal();
		if (principal instanceof UserDetails) {
			String username = ((UserDetails) principal).getUsername();
			MyUser user = ur.findByUsername(username);
			Vote vote = vr.findAllBySolutionIdAndUser(solution_id, user);
			if (vote == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			String json = objectMapper.writerWithView(VoteView.class).writeValueAsString(vote);
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(json);
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	
	@PostMapping("votes/")
	public ResponseEntity<String> vote(@RequestBody Vote v) throws JsonProcessingException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object principal = auth.getPrincipal();
		if (principal instanceof UserDetails) {
			String username = ((UserDetails) principal).getUsername();
			MyUser user = ur.findByUsername(username);
			Solution solution = v.getSolution();
			Boolean voteType = v.getVoteType(); 
			Vote vote = vr.findAllBySolutionIdAndUser(solution.getId(), user);		
			if (vote == null) {
				vote = new Vote(voteType, user, solution);
			}
			vote.setVoteType(voteType);
			System.out.println("4");			
			vr.save(vote);
			String json = objectMapper.writerWithView(VoteView.class).writeValueAsString(vote);
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(json);
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
}
