package peaksoft.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Shabdanov Ilim
 **/
@Entity
@Table(name = "hospitals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Hospital {
    @Id
    @SequenceGenerator(name = "hospital_gen",sequenceName = "hospital_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "hospital_gen")
    private Long id;
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2,max = 30,message = "Name should be between 2 and 30 characters")
    private String name;
    @NotEmpty(message = "Address should not be empty")
    @Size(min = 2,max = 30,message = "Address should be between 2 and 30 characters")
    private String address;
    @Column(length = 1000)
    private String image;
    @OneToMany(mappedBy = "hospital",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Doctor> doctors = new ArrayList<>();
    @OneToMany(mappedBy = "hospital",cascade = {CascadeType.ALL},fetch = FetchType.EAGER)
    private List<Patient> patients = new ArrayList<>();
    @OneToMany(mappedBy = "hospital",cascade = CascadeType.ALL)
    private List<Department> departments = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Appointment> appointments = new ArrayList<>();

}
