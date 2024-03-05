package Goals;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Goal implements Serializable, Comparable<Goal> {
    @Serial
    private static final long serialVersionUID = 1L;

    private String goalName;
    private String description;
    private LocalDate deadline;

    public Goal(String goalName, String description, LocalDate deadline) {
        this.goalName = goalName;
        this.description = description;
        this.deadline = deadline;
    }

    public String getGoalName() {
        return goalName;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goal goal = (Goal) o;
        return Objects.equals(goalName, goal.goalName) &&
                Objects.equals(description, goal.description) &&
                Objects.equals(deadline, goal.deadline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(goalName, description, deadline);
    }

	@Override
	public int compareTo(Goal otherGoal) {
		// TODO Auto-generated method stub
		return this.deadline.compareTo(otherGoal.deadline);
	}
}

