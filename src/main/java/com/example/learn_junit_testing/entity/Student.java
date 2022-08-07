package com.example.learn_junit_testing.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@Data
@Entity
@Table(
        name = "tbl_student",
        uniqueConstraints = @UniqueConstraint(columnNames = "emailId")
)
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "student_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long studentId;
    private String firstName;
    private String lastName;

    @Column(name = "emailId", nullable = false)
    private String emailId;
}
