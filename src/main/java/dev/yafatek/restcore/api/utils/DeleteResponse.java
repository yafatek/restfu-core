package dev.yafatek.restcore.api.utils;

public class DeleteResponse {
    protected String deleteCode;

    public DeleteResponse() {
    }

    public DeleteResponse(String deleteCode) {
        this.deleteCode = deleteCode;
    }

    public String getDeleteCode() {
        return deleteCode;
    }

    public void setDeleteCode(String deleteCode) {
        this.deleteCode = deleteCode;
    }
}
