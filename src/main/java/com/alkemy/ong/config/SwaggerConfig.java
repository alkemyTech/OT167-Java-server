package com.alkemy.ong.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String TITLE = "Somos más";
    private static final String DESCRIPTION = "Since 1997 at Somos Más we have been working with boys and girls\n" +
            "moms and poops, grandparents and residents of the La Cava neighborhood generating\n" +
            "processes of growth and social insertion. Joining the hands of\n" +
            "all families, those who live in the neighborhood and those who live outside\n" +
            "him, is that we can think, create and guarantee these processes. Are\n" +
            "a non-profit civil association that was created in 1997 with the\n" +
            "intention of giving food to the families of the neighbourhood. Over time\n" +
            "we were getting involved with the community and enlarging and improving\n" +
            "our ability to work.";

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(new Info().title(TITLE)
                        .description(DESCRIPTION)
                );
    }

}
