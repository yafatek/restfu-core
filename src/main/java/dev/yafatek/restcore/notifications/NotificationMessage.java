package dev.yafatek.restcore.notifications;


/**
 * POJO wrapper for a notification.
 *
 * @author Feras E Alawadi
 * @version 1.0.101
 * @since 1.0.101
 */
public class NotificationMessage {
    /**
     * the origin of the notification
     */
    private String origin;

    /**
     * the destination to the notification, in our case its the mobile or the admin panel
     */
    private String destination;
    /**
     * the content of the notification.
     */
    private String content;
    /**
     * the note about the notification.
     */
    private String notes;
    /**
     * timestamp of the notification
     */
    private String created;

    /**
     * default empty constructor
     */
    public NotificationMessage() {
    }

    /**
     * constructor to build the notification object.
     *
     * @param origin      the origin of the notification
     * @param destination the destination of the notification
     * @param content     the content of the notification
     * @param notes       notes about the notification
     * @param created     timestamp about the notification.
     */
    public NotificationMessage(String origin, String destination, String content, String notes, String created) {
        this.origin = origin;
        this.destination = destination;
        this.content = content;
        this.notes = notes;
        this.created = created;
    }


    /**
     * get the origin of the notification
     *
     * @return String
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * set notification origin
     *
     * @param origin the origin to set.
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * get the notification destination
     *
     * @return destination
     */
    public String getDestination() {
        return destination;
    }

    /**
     * set the notification destination
     *
     * @param destination the destination of the notification
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * get the notification content
     *
     * @return the content of notification
     */
    public String getContent() {
        return content;
    }

    /**
     * set the notification content
     *
     * @param content content of it
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * get  the notes about the notification
     *
     * @return string
     */
    public String getNotes() {
        return notes;
    }

    /**
     * set the notes of the notification
     *
     * @param notes the notes about it
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * get the created date
     *
     * @return String, represents the date (instance).
     */
    public String getCreated() {
        return created;
    }

    /**
     * set the created date
     *
     * @param created the Instanct object as String.
     */
    public void setCreated(String created) {
        this.created = created;
    }
}
