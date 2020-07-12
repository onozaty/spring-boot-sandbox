package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class V2__Load_data extends BaseJavaMigration {

    /**
     * @see <a href="https://flywaydb.org/documentation/migrations#java-based-migrations">Java-based migrations -
     * Flyway</a>
     */
    @Override
    public void migrate(Context context) throws Exception {

        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(
                new SingleConnectionDataSource(context.getConnection(), true));

        jdbcTemplate.update(
                "INSERT INTO customers(first_name, last_name, address) VALUES (:firstName, :lastName, :address)",
                new MapSqlParameterSource()
                        .addValue("firstName", "Taro")
                        .addValue("lastName", "Yamada")
                        .addValue("address", "Chiba"));
    }
}
