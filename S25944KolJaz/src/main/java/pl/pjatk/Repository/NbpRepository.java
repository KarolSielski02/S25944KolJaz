package pl.pjatk.Repository;

import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.pjatk.Model.NbpModel;

@Repository
@Table(name = "nbp_model")
public interface NbpRepository extends JpaRepository<NbpModel, Integer> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO nbp_model ( start_date, end_date, query_time, currency, average) VALUES (?1,?2,?3,?4,?5)", nativeQuery = true)
    void addRecord(String startDate, String endDate, String queryTime, String currency, Double average);
}
