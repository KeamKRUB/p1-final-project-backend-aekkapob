// keam
package ku.cs.project_SE.repository;

import ku.cs.project_SE.entity.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, String> {

    void deleteByProjectName(String projectName);

    @Query("SELECT DISTINCT p.projectName FROM Project p")
    List<String> findDistinctProjectName();
}
