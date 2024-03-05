package Schedule;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalTime;

import Accounts.TimingxUser;
import Database.DatabaseManager;
import Goals.Goal;

public class ScheduleManager implements Serializable {
	
    public static void displaySchedule(List<String> schedule) {
        if (schedule.isEmpty()) {
            System.out.println("No scheduled activities.");
            return;
        }

        System.out.println("----- Schedule -----");
        for (String scheduleEntry : schedule) {
            System.out.println(scheduleEntry);
        }
    }

    public static List<String> generateSchedule(LocalDate currentDate, LocalDate endDate, TimingxUser user) {
        List<String> schedule = new ArrayList<>();

        while (!currentDate.isAfter(endDate)) {
            schedule.add(generateScheduleEntry(currentDate, user));
            currentDate = currentDate.plusDays(1);
        }

        return schedule;
    }

    private static String generateScheduleEntry(LocalDate currentDate, TimingxUser user) {

        List<Goal> userGoals = user.getGoals();
        
    	TimingxUser userPreferences = DatabaseManager.getPreferences(DatabaseManager.getLoggedInUser().getUserId());
    	
    	if (userGoals.isEmpty()) {
    		System.out.println("No goals found for the user.");
    	}
    	
        StringBuilder scheduleEntry = new StringBuilder("-----" + currentDate + "----\n");

        String[] daysOfWeek = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"};
        int dayOfWeekIndex = currentDate.getDayOfWeek().getValue() - 1;
        scheduleEntry.append("------ ").append(daysOfWeek[dayOfWeekIndex]).append(" ------\n");

        for(int hour = 8; hour < 22; hour++) {
        	if (hour < 12) {
        		for(Goal goal : userGoals) {
        			if (goal.getGoalName().equals("Read Books")) {
        				if (userPreferences != null && "morning".equals(userPreferences.getBookTimePreference())) {
        					if(currentDate.isBefore(goal.getDeadline()) && !currentDate.isAfter(goal.getDeadline())) {
        						readBook(scheduleEntry,hour,goal);
        					}
        					else {
        						nothing(scheduleEntry,hour);
        					}
        				}
        			}
        			else if (goal.getGoalName().equals("Watch a Movie")) {
        				if (userPreferences != null && "morning".equals(userPreferences.getMovieTimePreference())) {
        					if (currentDate.isBefore(goal.getDeadline()) && !currentDate.isAfter(goal.getDeadline())) {
        						watchMovie(scheduleEntry,hour,goal);
        					}
        					else {
        						nothing(scheduleEntry,hour);
        					}
        				
        				}		
        			}
        			else if (goal.getGoalName().equals("Study/Work")) {
        				if (userPreferences != null && "morning".equals(userPreferences.getStudyTimePreference())) {
        					if (currentDate.isBefore(goal.getDeadline()) && !currentDate.isAfter(goal.getDeadline())) {
        						studentStudy(scheduleEntry,hour,goal);
        					}
        					else {
        						nothing(scheduleEntry,hour);
        					}
        				}
        				else if (userPreferences != null && "morning".equals(userPreferences.getWorkDayPreference())) {
        					if (currentDate.isBefore(goal.getDeadline()) && !currentDate.isAfter(goal.getDeadline())) {
        						employeeWork(scheduleEntry,hour,goal);
        					}
        					else {
        						nothing(scheduleEntry,hour);
        					}
        				}
        			}
        			else {
        				nothing(scheduleEntry,hour);
        			}
        		}
        	}
        	else if (hour >= 12 && hour < 18) {
        		for(Goal goal : userGoals) {
        			if (goal.getGoalName().equals("Read Books")) {
        				if (userPreferences != null && "day".equals(userPreferences.getBookTimePreference())) {
        					if(currentDate.isBefore(goal.getDeadline()) && !currentDate.isAfter(goal.getDeadline())) {
        						readBook(scheduleEntry,hour,goal);
        					}
        					else {
        						nothing(scheduleEntry,hour);
        					}
        				}
        			}
        			else if (goal.getGoalName().equals("Watch a Movie")) {
        				if (userPreferences != null && "day".equals(userPreferences.getMovieTimePreference())) {
        					if (currentDate.isBefore(goal.getDeadline()) && !currentDate.isAfter(goal.getDeadline())) {
        						watchMovie(scheduleEntry,hour,goal);
        					}
        					else {
        						nothing(scheduleEntry,hour);
        					}
        				
        				}		
        			}
        			else if (goal.getGoalName().equals("Study/Work")) {
        				if (userPreferences != null && "day".equals(userPreferences.getStudyTimePreference())) {
        					if (currentDate.isBefore(goal.getDeadline()) && !currentDate.isAfter(goal.getDeadline())) {
        						studentStudy(scheduleEntry,hour,goal);
        					}
        					else {
        						nothing(scheduleEntry,hour);
        					}
        				}
        				else if (userPreferences != null && "day".equals(userPreferences.getWorkDayPreference())) {
        					if (currentDate.isBefore(goal.getDeadline()) && !currentDate.isAfter(goal.getDeadline())) {
        						employeeWork(scheduleEntry,hour,goal);
        					}
        					else {
        						nothing(scheduleEntry,hour);
        					}
        				}
        			}
        			else {
        				nothing(scheduleEntry,hour);
        			}
        		}
        	}
        	else if (hour >= 18 && hour < 22) {
        		for(Goal goal : userGoals) {
        			if (goal.getGoalName().equals("Read Books")) {
        				if (userPreferences != null && "night".equals(userPreferences.getBookTimePreference())) {
        					if(currentDate.isBefore(goal.getDeadline()) && !currentDate.isAfter(goal.getDeadline())) {
        						readBook(scheduleEntry,hour,goal);
        					}
        					else {
        						nothing(scheduleEntry,hour);
        					}
        				}
        			}
        			else if (goal.getGoalName().equals("Watch a Movie")) {
        				if (userPreferences != null && "night".equals(userPreferences.getMovieTimePreference())) {
        					if (currentDate.isBefore(goal.getDeadline()) && !currentDate.isAfter(goal.getDeadline())) {
        						watchMovie(scheduleEntry,hour,goal);
        					}
        					else {
        						nothing(scheduleEntry,hour);
        					}
        				
        				}		
        			}
        			else if (goal.getGoalName().equals("Study/Work")) {
        				if (userPreferences != null && "night".equals(userPreferences.getStudyTimePreference())) {
        					if (currentDate.isBefore(goal.getDeadline()) && !currentDate.isAfter(goal.getDeadline())) {
        						studentStudy(scheduleEntry,hour,goal);
        					}
        					else {
        						nothing(scheduleEntry,hour);
        					}
        				}
        				else if (userPreferences != null && "night".equals(userPreferences.getWorkDayPreference())) {
        					if (currentDate.isBefore(goal.getDeadline()) && !currentDate.isAfter(goal.getDeadline())) {
        						employeeWork(scheduleEntry,hour,goal);
        					}
        					else {
        						nothing(scheduleEntry,hour);
        					}
        				}
        			}
        			else {
        				nothing(scheduleEntry,hour);
        			}
        		}
        	}
        }
        return scheduleEntry.toString();
    }
    
    private static void readBook(StringBuilder scheduleEntry,int hour,Goal goal) {
    	
    	LocalTime currentTime = LocalTime.of(hour, 0);
        
        String timeSlot = currentTime + "-" + currentTime.plusHours(1);
        
		
		String activity = goal.getDescription();

        scheduleEntry.append(timeSlot).append(" - ").append(activity).append("\n");
    }
    private static void watchMovie(StringBuilder scheduleEntry,int hour,Goal goal) {
    	LocalTime currentTime = LocalTime.of(hour, 0);
        
        String timeSlot = currentTime + "-" + currentTime.plusHours(1);
        
		
		String activity = goal.getDescription();

        scheduleEntry.append(timeSlot).append(" - ").append(activity).append("\n");
    }
    private static void studentStudy(StringBuilder scheduleEntry,int hour,Goal goal) {
    	LocalTime currentTime = LocalTime.of(hour, 0);
        
        String timeSlot = currentTime + "-" + currentTime.plusHours(1);
        
		
		String activity = goal.getDescription();

        scheduleEntry.append(timeSlot).append(" - ").append(activity).append("\n");
    }
    private static void employeeWork(StringBuilder scheduleEntry,int hour,Goal goal) {
    	LocalTime currentTime = LocalTime.of(hour, 0);
        
        String timeSlot = currentTime + "-" + currentTime.plusHours(1);
        
		
		String activity = goal.getDescription();

        scheduleEntry.append(timeSlot).append(" - ").append(activity).append("\n");
    }
    private static void nothing(StringBuilder scheduleEntry,int hour) {
    	LocalTime currentTime = LocalTime.of(hour, 0);
        
        String timeSlot = currentTime + "-" + currentTime.plusHours(1);
        
		
		String activity = "Nothing";

        scheduleEntry.append(timeSlot).append(" - ").append(activity).append("\n");
    }
}