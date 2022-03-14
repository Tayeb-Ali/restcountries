package dev.amatos.restcountries.controller;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import dev.amatos.restcountries.domain.ICountryRestSymbols;
import dev.amatos.restcountries.domain.ResponseEntity;
import dev.amatos.restcountries.domain.base.BaseCountry;
import io.micronaut.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class ControllerHelper {

  protected static final String[] V3_COUNTRY_FIELDS = new String[]{
      "name",
      "tld",
      "cca2",
      "ccn3",
      "cca3",
      "cioc",
      "independent",
      "status",
      "unMember",
      "currencies",
      "idd",
      "capital",
      "altSpellings",
      "region",
      "subregion",
      "languages",
      "translations",
      "latlng",
      "landlocked",
      "borders",
      "area",
      "flags",
      "demonyms",
      "population",
      "flags",
      "flag",
      "maps",
      "population",
      "gini",
      "fifa",
      "car",
      "timezones",
      "continents",
      "coatOfArms",
      "startOfWeek",
      "capitalInfo",
      "postalCode"
  };

  protected static final String[] COUNTRY_FIELDS_V2 = new String[]{
      "name",
      "topLevelDomain",
      "alpha2Code",
      "alpha3Code",
      "callingCodes",
      "capital",
      "altSpellings",
      "region",
      "subregion",
      "translations",
      "population",
      "latlng",
      "demonym",
      "area",
      "gini",
      "timezones",
      "borders",
      "nativeName",
      "numericCode",
      "currencies",
      "languages",
      "flags",
      "regionalBlocs",
      "cioc",
      "independent",
      "continent",
      "borders",
      "flag",
      "flags"
  };

  protected static HttpResponse<Object> ok(Object object) {
    return HttpResponse.ok(object);
  }

  protected static HttpResponse<Object> notFound() {
    var gson = new Gson();
    var notFound = Response
        .status(Status.NOT_FOUND)
        .entity(gson.toJson(new ResponseEntity(Response.Status.NOT_FOUND.getStatusCode(),
            Response.Status.NOT_FOUND.getReasonPhrase()))).build().getEntity();
    return HttpResponse.notFound(notFound);
  }

  protected static HttpResponse<Object> internalError() {
    var gson = new Gson();
    var notFound = Response
        .status(Status.INTERNAL_SERVER_ERROR)
        .entity(gson.toJson(new ResponseEntity(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
            Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase()))).build().getEntity();
    return HttpResponse.serverError(notFound);
  }

  protected static HttpResponse<Object> badRequest() {
    var gson = new Gson();
    var notFound = Response
        .status(Status.BAD_REQUEST)
        .entity(gson.toJson(new ResponseEntity(Response.Status.BAD_REQUEST.getStatusCode(),
            Response.Status.BAD_REQUEST.getReasonPhrase()))).build().getEntity();
    return HttpResponse.badRequest(notFound);
  }

  protected static Object parsedCountry(Set<? extends BaseCountry> countries, String fields) {
    if (fields == null || fields.isEmpty()) {
      return countries;
    } else {
      StringBuilder result = new StringBuilder();
      countries.forEach(country -> result.append(
          getCountryJson(country, Arrays.asList(fields.split(ICountryRestSymbols.COLON)))));
      return result;
    }
  }

  private static String getCountryJson(BaseCountry country, List<String> fields) {
    var gson = new Gson();
    var parser = new JsonParser();
    var jsonObject = parser.parse(gson.toJson(country)).getAsJsonObject();
    List<String> excludedFields = new ArrayList<>(Arrays.asList(V3_COUNTRY_FIELDS));
    excludedFields.removeAll(fields);
    excludedFields.forEach(jsonObject::remove);
    return jsonObject.toString();
  }

}
