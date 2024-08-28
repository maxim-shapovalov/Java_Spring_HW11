package ru.gb.spring.my_timesheet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.spring.my_timesheet.model.Timesheet;

import java.time.LocalDate;
import java.util.List;

public interface TimesheetRepository extends JpaRepository<Timesheet, Long> {
    // JPA Query methods - читать
    // CreatedAt - имя переменной в Timesheet, указывается после findBy
    List<Timesheet> findByCreatedAtAfter(LocalDate date);
    List<Timesheet> findByCreatedAtBefore(LocalDate date);


//    /* , NamedEntityRepository<Timesheet, Long> */ {

        // select * from timesheet where project_id = $1
        // Note: сломается, если в БД результат выдает больше одного значения
        // List<Timesheet> findByProjectId(Long projectId);

//  default List<Timesheet> findByCreatedAtBetweenUnsafe(LocalDate min, LocalDate max) {
//    if (min == null && max == null) {
//      return findAll();
//    } else if (min == null) {
//      return findByCreatedAtLessThan(max);
//    }
//  }

        // select * from timesheet where created_at > $1 and created_at < $2
//        List<Timesheet> findByCreatedAtBetween (LocalDate min, LocalDate max);

        // select * from timesheet where project_id = $1
        // order by created_at desc
        // jql - java query language, можно использовать когда нужен объект
//        @Query("select t from Timesheet t where t.projectId = :projectId order by t.createdAt desc")
//        List<Timesheet> findByProjectId (Long projectId);

    // обычные SQL запросы(как ниже, с обозначением native=true, можно использовать, когда нужно получить значение(я)
//  @Query(nativeQuery = true, value = "select * from timesheet where project_id = :projectId")
//  List<Long> findIdsByProjectId(Long projectId);

//  @Query(nativeQuery = true, value = "update timesheet set active = false where project_id = :projectId")
//  @Modifying
//  void deactivateTimesheetsWithProjectId(Long projectId);


//  @Query("select t.id from Timesheet t where t.projectId = :projectId order by t.createdAt desc")
//  List<Long> findIdsByProjectId(Long projectId);

        // select * from timesheet where project_id = $1
        // Note: сломается, если в БД результат выдает больше одного значения
        // Optional<Timesheet> findByProjectId(Long projectId);

        // select * from timesheet where project_id = $1
        // order by created_at desc
        // List<Timesheet> findByProjectIdOrderByCreatedAtDesc(Long projectId);

        // select * from timesheet where project_id = $1 or minutes = $2
        // List<Timesheet> findByProjectIdOrMinutes(Long projectId, Integer minutes);

        // ... where project_name like '%projectNameLike%'
        // List<Timesheet> findByProjectNameLike(String projectNameLike);
//    }
}
