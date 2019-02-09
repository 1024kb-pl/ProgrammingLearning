package pl._1024kb.stowarzyszenienaukijavy.simpletodo.controller.servlets.task;

import pl._1024kb.stowarzyszenienaukijavy.simpletodo.model.entity.Task;
import pl._1024kb.stowarzyszenienaukijavy.simpletodo.model.service.OrderOption;
import pl._1024kb.stowarzyszenienaukijavy.simpletodo.model.service.TaskServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@WebServlet("/tasks")
public class TasksListServlet extends HttpServlet
{
    private TaskServiceImpl taskService = TaskServiceImpl.getInstance();
    private List<Task> taskList = new LinkedList<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String username = request.getSession(false).getAttribute("username").toString();

        taskList = taskService.getAllTasksByUserId(username);

        filterTask(username, request);

        String sortList = request.getParameter("sort");
        if(sortList != null)
            sortTaskList(username, sortList);

        request.setAttribute("tasksList", taskList);
        request.getRequestDispatcher("taskslist.jsp").forward(request, response);
    }

    private void sortTaskList(String username, String sortList)
    {
        OrderOption orderOption = OrderOption.valueOf(sortList.toUpperCase());
        switch (orderOption)
        {
            case TITLE:
                taskList = taskService.getAllTasksOrderedByTitle(username);
                break;
            case DATE:
                taskList = taskService.getAllTasksOrderedByDate(username);
                break;
            case STATUS:
                taskList = taskService.getAllTasksOrderedByStatus(username);
                break;
        }
    }

    private void filterTask(String username, HttpServletRequest request)
    {
        String filterOption = request.getParameter("filter");
        if(filterOption != null && !filterOption.isEmpty())
        {
            if (!filterOption.equals("date"))
            {
                taskList = taskService.getAllTasksByTaskDone(username, filterOption);
                request.setAttribute("done_filter", filterOption);
            } else
            {
                String dateStr = request.getParameter("dateFilter");
                LocalDate dateFilter = LocalDate.parse(dateStr);
                taskList = taskService.getAllTasksByDate(username, dateFilter);
                request.setAttribute("date_filter", dateFilter);
            }
        }
    }

}
