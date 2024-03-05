package Accounts;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Goals.*;

public abstract class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private int userId;
    private String username;
    private String password;

    //Предпочтение
    private String bookPreference;
    private String bookTimePreference;
    private String moviePreference;
    private String movieTimePreference;
    private String occupationPreference;
    private String studyTimePreference;
    private String workDayPreference;
    
    //Цель
    protected List<Goal> goals = new ArrayList<>();
    
    public User() {

    }

    public User(int userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    


    public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBookPreference() {
        return bookPreference;
    }

    public void setBookPreference(String bookPreference) {
        this.bookPreference = bookPreference;
    }

    public String getBookTimePreference() {
        return bookTimePreference;
    }

    public void setBookTimePreference(String bookTimePreference) {
        this.bookTimePreference = bookTimePreference;
    }

    public String getMoviePreference() {
        return moviePreference;
    }

    public void setMoviePreference(String moviePreference) {
        this.moviePreference = moviePreference;
    }

    public String getMovieTimePreference() {
        return movieTimePreference;
    }

    public void setMovieTimePreference(String movieTimePreference) {
        this.movieTimePreference = movieTimePreference;
    }

    public String getOccupationPreference() {
        return occupationPreference;
    }

    public void setOccupationPreference(String occupationPreference) {
        this.occupationPreference = occupationPreference;
    }

    public String getStudyTimePreference() {
        return studyTimePreference;
    }

    public void setStudyTimePreference(String studyTimePreference) {
        this.studyTimePreference = studyTimePreference;
    }

    public String getWorkDayPreference() {
        return workDayPreference;
    }

    public void setWorkDayPreference(String workDayPreference) {
        this.workDayPreference = workDayPreference;
    }
    
    public List<Goal> getGoals() {
        return goals;
    }

    public void addGoal(Goal goal) {
        if (this.goals == null) {
            this.goals = new ArrayList<>();
        }
        this.goals.add(goal);
    }

    public void removeGoal(Goal goal) {
        goals.remove(goal);
    }
    public void setGoals(List<Goal> goals) {
        this.goals = goals;
    }
    
    public abstract void menu(Scanner scanner);

	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", password=" + password + ", bookPreference="
				+ bookPreference + ", bookTimePreference=" + bookTimePreference + ", moviePreference=" + moviePreference
				+ ", movieTimePreference=" + movieTimePreference + ", occupationPreference=" + occupationPreference
				+ ", studyTimePreference=" + studyTimePreference + ", workDayPreference=" + workDayPreference
				+ ", goals=" + goals + "]";
	}

    
}
