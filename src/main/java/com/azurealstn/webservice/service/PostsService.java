package com.azurealstn.webservice.service;

import com.azurealstn.webservice.controller.dto.PostsListResponseDto;
import com.azurealstn.webservice.controller.dto.PostsResponseDto;
import com.azurealstn.webservice.controller.dto.PostsSaveRequestDto;
import com.azurealstn.webservice.controller.dto.PostsUpdateRequestDto;
import com.azurealstn.webservice.domain.posts.Posts;
import com.azurealstn.webservice.domain.posts.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        postsRepository.delete(posts);
    }

    @Transactional
    public Page<Posts> getPostList(Pageable pageable) {
        return postsRepository.findAll(pageable);
    }

    @Transactional
    public List<Posts> search(String keyword) {
        List<Posts> postsList = postsRepository.findByTitleContaining(keyword);
        return postsList;
    }

    @Transactional
    public Boolean getListCheck(Pageable pageable) {
        Page<Posts> saved = getPostList(pageable);
        Boolean check = saved.hasNext();
        return check;
    }
}
