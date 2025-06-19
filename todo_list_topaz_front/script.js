const URI = "http://localhost:8080/tarefas"

//método que vai para a tela de criar a tarefa
function irParaTelaDeCriarTarefa() {
    document.getElementById("overlay").classList.remove("overlay-hidden");
    document.body.style.overflow = "hidden";
}

//Listener para quando clicar em cancelar voltar para a lista de tarefas
document.getElementById("btnCancelar").addEventListener("click", () => {
    document.getElementById("overlay").classList.add("overlay-hidden");
    document.body.style.overflow = "auto"; // libera scroll
});

//Listener para quando clicar em enviar o request usar a solicitação de criar tarefa
document.getElementById("formCriarTarefa").addEventListener("submit", (e) => {
    e.preventDefault();

    const titulo = document.getElementById("titulo").value;
    const descricao = document.getElementById("descricao").value;
    const status = document.getElementById("status").value;
    console.log("Criando tarefa:", { titulo, descricao, status });

    const tarefa = { titulo, descricao, status };
    //Requisição da api
    putTarefa(tarefa)
    //Adiciona tarefa ao quadro
    adicionarTarefaAoKanban(tarefa)
    // Fecha o popup após criação
    document.getElementById("overlay").classList.add("overlay-hidden");
    document.body.style.overflow = "auto";
    //Limpa o form
    e.target.reset();
});

//TODO método para fazer o request de post criar tarefa
function putTarefa(tarefa) {

    fetch(URI, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(tarefa)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Erro na requisição: " + response.status);
            }
            return response.json();
        })
        .then(data => {
            console.log("Resposta:", data);
            return data;
        })
        .catch(error => {
            console.error("Erro ao fazer a requisição:", error);
        });
}

function adicionarTarefaAoKanban(tarefa) {
    //Cria o botao
    const cardBtn = document.createElement("button");
    cardBtn.classList.add("card", "mb-3", "kanban-card");
    cardBtn.type = "button";
    cardBtn.style.textAlign = "left";

    //Coloca o id da tarefa como atributo data para usar depois nas requisições de edição e delete
    cardBtn.dataset.tarefaId = tarefa.id;

    //Cria o corpo do card dentro do botão
    const cardBody = document.createElement("div");
    cardBody.classList.add("card-body");
    cardBody.id = `tarefa-${tarefa.id}`; //id pra pegar depois nas requisições

    // Título da tarefa
    const cardTitle = document.createElement("h5");
    cardTitle.classList.add("card-title");
    cardTitle.innerText = tarefa.titulo;

    // Descrição resumida (100 chars ou menos)
    const descricaoResumida = tarefa.descricao.length > 100
        ? tarefa.descricao.substring(0, 100) + "..."
        : tarefa.descricao;

    const cardText = document.createElement("p");
    cardText.classList.add("card-text");
    cardText.innerText = descricaoResumida;

    // Monta o card
    cardBody.appendChild(cardTitle);
    cardBody.appendChild(cardText);
    cardBtn.appendChild(cardBody);

    // Adiciona o click para abrir detalhes da tarefa
    cardBtn.addEventListener("click", () => {
        //requisição getById usando tarefa.id
        fetch(`${URI}/${tarefa.id}`)
            .then(response => {
                if (!response.ok) throw new Error("Erro ao buscar tarefa: " + response.status);
                return response.json();
            })
            .then(detalhes => {
                //Abre o popup com os detalhes da tarefa
                abrirPopupComDetalhes(detalhes);
            })
            .catch(error => {
                console.error(error);
            });
    });

    //encontra a coluna correta com base no status
    const coluna = document.getElementById(tarefa.status);
    if (coluna) {
        coluna.appendChild(cardBtn);
    } else {
        console.warn("Coluna não encontrada para status:", tarefa.status);
    }
}

function abrirPopupComDetalhes(detalhes) {
    const popup = document.getElementById("popupDetalhes");
    const popupContent = popup.querySelector(".popup-content");

    // Função para montar a visualização padrão
    const montarVisualizacao = () => {
        popupContent.innerHTML = `
      <button id="btnFecharPopup" class="btn-close">×</button>
      <h3 id="popupTitulo">${detalhes.titulo}</h3>
      <p id="popupDescricao">${detalhes.descricao}</p>
      <p><strong>Status:</strong> <span id="popupStatus">${detalhes.status}</span></p>
      <div class="mt-3">
        <button id="btnEditar" class="btn btn-warning">Editar</button>
        <button id="btnApagar" class="btn btn-danger ms-2">Apagar</button>
      </div>
    `;

        //Listeners da visualização
        document.getElementById("btnFecharPopup").onclick = fecharPopup;
        document.getElementById("btnEditar").onclick = montarFormularioEdicao;
        document.getElementById("btnApagar").onclick = () => deletarTarefa(detalhes.id);
    };

    //Função para fechar popup
    const fecharPopup = () => {
        popup.classList.add("overlay-hidden");
        document.body.style.overflow = "auto";
    };

    //Função para deletar a tarefa
    const deletarTarefa = (id) => {
        if (confirm("Tem certeza que deseja apagar a tarefa?")) {
            fetch(`${URI}/${id}`, {
                method: "DELETE"
            })
                .then(response => {
                    if (!response.ok) throw new Error("Erro ao apagar tarefa");
                    alert("Tarefa apagada com sucesso!");

                    const card = document.querySelector(`[data-tarefa-id="${id}"]`);
                    if (card) card.remove();

                    fecharPopup();
                })
                .catch(error => {
                    alert("Erro ao apagar tarefa: " + error.message);
                });
        }
    };

    //Função para montar o formulário de edição
    const montarFormularioEdicao = () => {
        popupContent.innerHTML = `
      <button id="btnFecharPopup" class="btn-close">×</button>
      <h3>Editar tarefa</h3>
      <form id="formEditarTarefa">
        <div class="mb-3">
          <label for="editarTitulo" class="form-label">Título</label>
          <input type="text" id="editarTitulo" class="form-control" required value="${detalhes.titulo}" />
        </div>
        <div class="mb-3">
          <label for="editarDescricao" class="form-label">Descrição</label>
          <textarea id="editarDescricao" class="form-control" required rows="3">${detalhes.descricao}</textarea>
        </div>
        <div class="mb-3">
          <label for="editarStatus" class="form-label">Status</label>
          <select id="editarStatus" class="form-select" required>
            <option value="NAO_INICIADO" ${detalhes.status === "NAO_INICIADO" ? "selected" : ""}>Não iniciado</option>
            <option value="EM_ANDAMENTO" ${detalhes.status === "EM_ANDAMENTO" ? "selected" : ""}>Em andamento</option>
            <option value="CONCLUIDA" ${detalhes.status === "CONCLUIDA" ? "selected" : ""}>Concluído</option>
            <option value="PAUSADA" ${detalhes.status === "PAUSADA" ? "selected" : ""}>Pausado</option>
          </select>
        </div>
        <button type="submit" class="btn btn-success">Salvar</button>
        <button type="button" id="btnCancelarEdicao" class="btn btn-secondary ms-2">Cancelar</button>
      </form>
    `;

        document.getElementById("btnFecharPopup").onclick = fecharPopup;

        document.getElementById("btnCancelarEdicao").onclick = () => {
            montarVisualizacao();
        };

        document.getElementById("formEditarTarefa").onsubmit = (e) => {
            e.preventDefault();

            const tarefaEditada = {
                titulo: document.getElementById("editarTitulo").value,
                descricao: document.getElementById("editarDescricao").value,
                status: document.getElementById("editarStatus").value
            };

            fetch(`${URI}/${detalhes.id}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(tarefaEditada)
            })
                .then(response => {
                    if (!response.ok) throw new Error("Erro ao editar tarefa");
                    return response.json();
                })
                .then(tarefaAtualizada => {
                    alert("Tarefa atualizada com sucesso!");

                    const card = document.querySelector(`[data-tarefa-id="${detalhes.id}"]`);
                    if (card) card.remove();

                    adicionarTarefaAoKanban({ id: detalhes.id, ...tarefaEditada });

                    fecharPopup();
                })
                .catch(error => {
                    alert("Erro ao salvar tarefa: " + error.message);
                });
        };
    };

    // Inicia exibindo a visualização padrão
    montarVisualizacao();

    // Abre popup
    popup.classList.remove("overlay-hidden");
    document.body.style.overflow = "hidden";
}

function getTarefas() {
    fetch(URI)
        .then(response => {
            if (!response.ok) {
                throw new Error("Erro na requisição: " + response.status);
            }
            return response.json();
        })
        .then(listaDeTarefas => {
            listaDeTarefas.forEach(tarefa => {
                adicionarTarefaAoKanban(tarefa);
            });
            return listaDeTarefas;
        })
        .catch(error => {
            console.error("Erro ao fazer a requisição:", error);
        });
}
//inicializa a aplicação e popula as tarefas usando o /tarefas -> getAll()
getTarefas();

