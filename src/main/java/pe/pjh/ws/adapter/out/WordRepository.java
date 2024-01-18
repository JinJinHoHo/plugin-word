package pe.pjh.ws.adapter.out;

import pe.pjh.ws.application.port.out.WordBatchPort;
import pe.pjh.ws.application.port.out.WordManagerPort;
import pe.pjh.ws.application.port.out.WordSearchPort;

public interface WordRepository extends WordSearchPort, WordManagerPort, WordBatchPort {
}
