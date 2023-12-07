package com.example.tiary.article.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.tiary.category.entity.Category;
import com.example.tiary.global.audit.AuditingFields;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
@Entity
public class Article extends AuditingFields {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;

	@Column(length = 65554)
	private String content;

	private Long view;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@OneToMany(mappedBy = "article")
	@ToString.Exclude
	private List<ArticleHashtag> articleHashtags = new ArrayList<>();

	public static Article of(Long id, String title, String content, Long view, Category category, List<ArticleHashtag> articleHashtags) {
		return new Article(id, title, content, view, category,articleHashtags);
	}
	public void updateTitle(String title) {
		this.title = title;
	}
	public void updateContent(String content) {
		this.content = content;
	}
	public void updateCategory(Category category){
		this.category = category;
	}
}
