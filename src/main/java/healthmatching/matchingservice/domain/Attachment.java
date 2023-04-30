package healthmatching.matchingservice.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attachment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //  원본이름
    @Column(name = "originfilename")
    private String originFilename;
    //  파일저장한이름
    @Column(name = "storefilename")
    private String storeFilename;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;
    @Builder
    public Attachment(String originFilename, String storeFilename) {
        this.originFilename = originFilename;
        this.storeFilename = storeFilename;
    }
}
