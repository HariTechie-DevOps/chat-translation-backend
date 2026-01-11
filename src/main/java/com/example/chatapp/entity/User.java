import com.fasterxml.jackson.annotation.JsonProperty;
// ... other imports

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("username") // This bridges the gap!
    private String name;

    private String language;
}
