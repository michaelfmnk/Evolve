package com.dreamteam.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetailDto {
    private String title;
    private int status;
    private String detail;
    private long timestamp;
    private String devMessage;

    public static ErrorDetailDtoBuilder builder() {
        return new ErrorDetailDtoBuilder();
    }

    public static class ErrorDetailDtoBuilder {
        private String title;
        private int status;
        private String detail;
        private long timestamp;
        private String devMessage;

        ErrorDetailDtoBuilder() {
        }

        public ErrorDetailDtoBuilder status(HttpStatus status) {
            this.status = status.value();
            this.title = status.name();
            return this;
        }

        public ErrorDetailDtoBuilder cause(Throwable cause) {
            this.detail = cause.getMessage();
            this.devMessage = cause.getClass().getName();
            return this;
        }

        public ErrorDetailDtoBuilder timeStamp(Date date) {
            this.timestamp = date.getTime();
            return this;
        }

        public ErrorDetailDto build() {
            return new ErrorDetailDto(title, status, detail, timestamp, devMessage);
        }

        public String toString() {
            return "ErrorDetailDto.ErrorDetailDtoBuilder(title=" + this.title + ", status=" + this.status + ", detail=" + this.detail + ", timestamp=" + this.timestamp + ", devMessage=" + this.devMessage + ")";
        }
    }
}
