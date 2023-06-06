package com.example.proiect.model;
import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Table(name = "Orders")
public class Order implements com.example.proiect.model.Entity<Integer>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_personnel")
    private MedicalPersonnel medicalPersonnel;
    @ManyToOne
    @JoinColumn(name = "id_pharmaceutist")
    private Pharmaceutist pharmaceutist;
    @ManyToOne
    @JoinColumn(name = "id_medication")
    private Medication medication;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "time_taken")
    private String time_taken;
    @Column(name = "comments")
    private String comments;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
    public Order() {
    }
    public Order(int idPersonnel, int idMedication, int quantity, String timeTaken, String comments, Status status) {
        this.medicalPersonnel=new MedicalPersonnel();
        medicalPersonnel.setId(idPersonnel);
        this.pharmaceutist=null;
        this.medication=new Medication();
        medication.setId(idMedication);
        this.quantity=quantity;
        this.time_taken = timeTaken;
        this.comments=comments;
        this.status=status;
    }
    @Override
    public Integer getId(){
        return id;
    }

    @Override
    public void setId(Integer id){
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTime_taken() {
        return time_taken;
    }

    public void setTime_taken(String time_taken) {
        this.time_taken = time_taken;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    public MedicalPersonnel getMedicalPersonnel() {
        return medicalPersonnel;
    }

    public void setMedicalPersonnel(MedicalPersonnel medicalPersonnel) {
        this.medicalPersonnel = medicalPersonnel;
    }

    public Pharmaceutist getPharmaceutist() {
        return pharmaceutist;
    }

    public void setPharmaceutist(Pharmaceutist pharmaceutist) {
        this.pharmaceutist = pharmaceutist;
    }

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }
}
