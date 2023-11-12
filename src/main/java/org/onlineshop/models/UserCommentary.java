package org.onlineshop.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class UserCommentary {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private String userComment;
}
