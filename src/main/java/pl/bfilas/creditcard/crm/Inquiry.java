package pl.bfilas.creditcard.crm;

public class Inquiry {
    import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Inquiry {
    @Id
    private Long id;
    private String name;
    private String email;
    private String subject;
    private String description;
}
}
