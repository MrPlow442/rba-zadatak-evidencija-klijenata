FROM wiremock/wiremock:2.35.0

COPY ./wiremock/mappings /home/wiremock/mappings
COPY ./wiremock/__files /home/wiremock/__files

CMD ["--local-response-templating", "--port", "9001"]