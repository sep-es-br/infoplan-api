package br.gov.es.infoplan.service;

import br.gov.es.infoplan.dto.NomeValorObject;
import br.gov.es.infoplan.dto.capitacao.AllCapitacaoRow;
import br.gov.es.infoplan.dto.capitacao.CapitacaoFilter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CapitationService extends PentahoBIService {

  private final Logger LOGGER = LogManager.getLogger(PentahoBIService.class);

  @Value("${pentahoBI.capitation.path}")
  private String CaptationPath;

  @Value("${pentahoBI.capitation.target.dashAll}")
  private String targetDashAll;

  @Value("${pentahoBI.capitation.target.dashPrograms}")
  private String targetDashProgram;

  @Value("${pentahoBI.capitation.target.dashProjects}")
  private String targetDashProject;

  @Value("${pentahoBI.capitation.target.dashTotos}")
  private String targetDashTodos;

  @Value("${pentahoBI.capitation.dataAccessId.dashDatas}")
  private String dataAccessIdDashDatas;

  @Value("${pentahoBI.capitation.dataAccessId.programTotal}")
  private String dataAccessIdProgramTotal;

  @Value("${pentahoBI.capitation.dataAccessId.projectTotal}")
  private String dataAccessIdProjectTotal;

  // private final int mockAno = 2024;

  public List<NomeValorObject> getAllBySecretaria(String filtroJson) {

    ArrayList<NomeValorObject> retorno = new ArrayList<>();

    try {
      CapitacaoFilter filtro = new ObjectMapper().readValue(filtroJson, CapitacaoFilter.class);
      List<AllCapitacaoRow> allDatas = consultarTodosValores(filtro);

      allDatas.forEach(data -> {

        List<NomeValorObject> savedSecretaria = retorno.stream().filter(r -> r.getId() == data.getIdSecretaria())
            .toList();
        if (savedSecretaria.size() > 0) {
          savedSecretaria.get(0).addAmmount(data.getValor());
        } else {
          NomeValorObject novoItem = new NomeValorObject(
              data.getIdSecretaria(),
              data.getSecretaria(),
              data.getValor());

          retorno.add(novoItem);
        }

      });

      retorno.sort(byValue());

    } catch (Exception e) {
      e.printStackTrace();
    }

    return retorno;
  }

  public List<NomeValorObject> getAllByProjeto(String filtroJson) {

    ArrayList<NomeValorObject> retorno = new ArrayList<>();

    try {
      CapitacaoFilter filtro = new ObjectMapper().readValue(filtroJson, CapitacaoFilter.class);
      List<AllCapitacaoRow> allDatas = consultarTodosValores(filtro);

      allDatas.forEach(data -> {

        List<NomeValorObject> savedSecretaria = retorno.stream().filter(r -> r.getId() == data.getIdProjeto()).toList();
        if (savedSecretaria.size() > 0) {
          savedSecretaria.get(0).addAmmount(data.getValor());
        } else {
          NomeValorObject novoItem = new NomeValorObject(
              data.getIdProjeto(),
              data.getProjeto(),
              data.getValor());

          retorno.add(novoItem);
        }

      });

      retorno.sort(byValue());

    } catch (Exception e) {
      e.printStackTrace();
    }

    return retorno;
  }

  public List<NomeValorObject> getAllByPrograma(String filtroJson) {

    ArrayList<NomeValorObject> retorno = new ArrayList<>();

    try {
      CapitacaoFilter filtro = new ObjectMapper().readValue(filtroJson, CapitacaoFilter.class);
      List<AllCapitacaoRow> allDatas = consultarTodosValores(filtro);

      allDatas.forEach(data -> {

        List<NomeValorObject> savedSecretaria = retorno.stream().filter(r -> r.getId() == data.getIdPrograma())
            .toList();
        if (savedSecretaria.size() > 0) {
          savedSecretaria.get(0).addAmmount(data.getValor());
        } else {
          NomeValorObject novoItem = new NomeValorObject(
              data.getIdPrograma(),
              data.getPrograma(),
              data.getValor());

          retorno.add(novoItem);
        }

      });

      retorno.sort(byValue());

    } catch (Exception e) {
      e.printStackTrace();
    }

    return retorno;
  }

  public List<NomeValorObject> getAllByMicroregiao(String filtroJson) {

    ArrayList<NomeValorObject> retorno = new ArrayList<>();

    try {
      CapitacaoFilter filtro = new ObjectMapper().readValue(filtroJson, CapitacaoFilter.class);
      List<AllCapitacaoRow> allDatas = consultarTodosValores(filtro);

      allDatas.forEach(data -> {

        List<NomeValorObject> savedSecretaria = retorno.stream().filter(r -> r.getId() == data.getIdMicrorregiao())
            .toList();
        if (savedSecretaria.size() > 0) {
          savedSecretaria.get(0).addAmmount(data.getValor());
        } else {
          NomeValorObject novoItem = new NomeValorObject(
              data.getIdMicrorregiao(),
              data.getMicrorregiao(),
              data.getValor());

          retorno.add(novoItem);
        }

      });

      retorno.sort(byValue());

    } catch (Exception e) {
      e.printStackTrace();
    }

    return retorno;
  }

  public List<NomeValorObject> getAllByCidade(String filtroJson) {

    ArrayList<NomeValorObject> retorno = new ArrayList<>();

    try {
      CapitacaoFilter filtro = new ObjectMapper().readValue(filtroJson, CapitacaoFilter.class);
      List<AllCapitacaoRow> allDatas = consultarTodosValores(filtro);

      allDatas.forEach(data -> {

        List<NomeValorObject> savedSecretaria = retorno.stream().filter(r -> r.getId() == data.getIdCidade()).toList();
        if (savedSecretaria.size() > 0) {
          savedSecretaria.get(0).addAmmount(data.getValor());
        } else {
          NomeValorObject novoItem = new NomeValorObject(
              data.getIdCidade(),
              data.getCidade(),
              data.getValor());

          retorno.add(novoItem);
        }

      });

      retorno.sort(byValue());

    } catch (Exception e) {
      e.printStackTrace();
    }

    return retorno;
  }

  public double getProgramTotal(String filterJson) {

    double value = -1d;

    try {

      CapitacaoFilter filter = new ObjectMapper().readValue(filterJson, CapitacaoFilter.class);
      List<AllCapitacaoRow> allDatas = consultarTodosValores(filter);

      value = 0d;
      for (int i = 0; i < allDatas.size(); i++) {
        value += allDatas.get(i).getValor();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return value;
  }

  public double getProjectTotal(String filterJson) {

    double value = -1d;

    try {
      CapitacaoFilter filter = new ObjectMapper().readValue(filterJson, CapitacaoFilter.class);
      List<AllCapitacaoRow> allDatas = consultarTodosValores(filter);

      value = 0d;
      for (int i = 0; i < allDatas.size(); i++) {
        value += allDatas.get(i).getValor();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return value;
  }

  protected List<Map<String, JsonNode>> filterResult(List<Map<String, JsonNode>> datas, Map<String, String> filters) {
    if (filters == null)
      return datas;

    List<Map<String, JsonNode>> result = new ArrayList<>(datas);

    for (Map.Entry<String, String> filter : filters.entrySet()) {
      result = result.stream()
          .filter(register -> register.get(filter.getKey()).asText().equals(filter.getValue().toString())).toList();
    }

    return result;
  }

  @Override
  protected String buildEndpointUri(String target, String dataAccess, Map<String, Object> params) {
    return super.buildEndpointUri(this.CaptationPath, target, dataAccess, params);
  }

  public List<AllCapitacaoRow> consultarTodosValores(CapitacaoFilter filter) {

    ArrayList<AllCapitacaoRow> retorno = new ArrayList<>();

    HashMap<String, Object> params = new HashMap<>();
    params.put("parampAno", "2024"); // filter.getAno());
    try {
      List<Map<String, JsonNode>> resultset = extractDataFromResponse(
          doRequest(buildEndpointUri(targetDashTodos, dataAccessIdDashDatas, params)));

      for (Map<String, JsonNode> rs : resultset) {

        AllCapitacaoRow acr = new AllCapitacaoRow();
        acr.setAno(rs.get("ano").asText(""));
        acr.setIdPrograma(rs.get("id_programa").asInt(0));
        acr.setPrograma(rs.get("programa").asText(""));
        acr.setIdProjeto(rs.get("id_projeto").asInt(0));
        acr.setProjeto(rs.get("projeto").asText(""));
        acr.setIdMicrorregiao(rs.get("id_microrregiao").asInt(0));
        acr.setMicrorregiao(rs.get("microrregiao").asText(""));
        acr.setIdCidade(rs.get("id_cidade").asInt(0));
        acr.setCidade(rs.get("cidade").asText(""));
        acr.setIdSecretaria(rs.get("id_secretaria").asInt(0));
        acr.setSecretaria(rs.get("secretaria").asText(""));
        acr.setValor(rs.get("valor").asDouble(0));

        retorno.add(acr);
      }
      ;

      return retorno.stream().filter(rs -> {
        return (filter.getIdMicrorregiao() == -1 || filter.getIdMicrorregiao() == rs.getIdMicrorregiao())
            && (filter.getIdCidade() == -1 || filter.getIdCidade() == rs.getIdCidade())
            && (filter.getIdPrograma() == -1 || filter.getIdPrograma() == rs.getIdPrograma())
            && (filter.getIdProjeto() == -1 || filter.getIdProjeto() == rs.getIdProjeto())
            && (filter.getIdSecretaria() == -1 || filter.getIdSecretaria() == rs.getIdSecretaria());
      }).toList();

    } catch (Exception e) {
      this.LOGGER.error(e);
      return List.of();
    }
  }

  private Comparator<NomeValorObject> byValue() {
    return (r1, r2) -> (int) (r2.getAmmount() * 100) - (int) (r1.getAmmount() * 100);
  }

}
