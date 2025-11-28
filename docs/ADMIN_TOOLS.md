# Ferramentas de Administração - NaTomada

## 🎯 Interfaces Web Disponíveis

### 1. **pgAdmin** - Gerenciamento do PostgreSQL

**URL**: http://localhost:5050

**Credenciais de Acesso**:
- **Email**: `admin@natomada.com` (padrão, pode alterar no .env com `PGADMIN_EMAIL`)
- **Senha**: `admin` (padrão, pode alterar no .env com `PGADMIN_PASSWORD`)

#### Como Conectar ao Banco de Dados

1. Acesse http://localhost:5050
2. Faça login com as credenciais acima
3. Clique em **"Add New Server"** (ou ícone de +)
4. Na aba **General**:
   - **Name**: `NaTomada`
5. Na aba **Connection**:
   - **Host**: `postgres` (nome do container)
   - **Port**: `5432`
   - **Database**: `natomada`
   - **Username**: `postgres`
   - **Password**: `postgres`
6. Clique em **Save**

Agora você pode:
- ✅ Visualizar todas as tabelas
- ✅ Executar queries SQL
- ✅ Ver dados em tempo real
- ✅ Criar backups
- ✅ Gerenciar usuários e permissões

---

### 2. **Grafana + Loki** - Visualização de Logs ⭐

**URL**: http://localhost:3001

**Credenciais de Acesso**:
- **Usuário**: `admin`
- **Senha**: `admin`

#### Como Visualizar os Logs

1. Acesse http://localhost:3001
2. Login: `admin` / `admin`
3. No menu lateral, clique em **"Explore"** (ícone de bússola)
4. Selecione **"Loki"** como datasource (já configurado automaticamente)

#### Queries Úteis

**Ver logs da aplicação**:
```logql
{container="natomada-app"}
```

**Filtrar apenas ERRORs**:
```logql
{container="natomada-app"} |= "ERROR"
```

**Ver eventos Kafka sendo publicados**:
```logql
{container="natomada-app"} |= "Publishing event"
```

**Ver eventos sendo consumidos**:
```logql
{container="natomada-app"} |= "Processing"
```

**Ver logs de todos os serviços**:
```logql
{service=~"app|postgres|kafka|redis"}
```

**Filtrar por nível de log**:
```logql
{container="natomada-app"} | json | level="ERROR"
```

#### Recursos do Grafana

- ✅ **Live Tail**: Ver logs em tempo real
- ✅ **Filtros**: Por container, nível, texto
- ✅ **Pesquisa**: Regex, texto completo
- ✅ **Timeline**: Visualização temporal dos logs
- ✅ **Contexto**: Clique em um log para ver logs antes/depois
- ✅ **Export**: Baixar logs filtrados

#### Exemplo: Debugar um Erro

1. No Grafana Explore, use:
   ```logql
   {container="natomada-app"} |= "ERROR"
   ```
2. Clique no log de erro
3. Clique em "Show Context" para ver o que aconteceu antes/depois
4. Veja o stack trace completo

---

### 3. **Kafka UI** - Monitoramento de Eventos

**URL**: http://localhost:8090

**Sem autenticação** - Acesso direto

Funcionalidades:
- ✅ Visualizar tópicos Kafka
- ✅ Ler mensagens (eventos) em tempo real
- ✅ Ver partições e offsets
- ✅ Monitorar consumer groups
- ✅ Ver payloads JSON completos dos eventos

**Tópicos Disponíveis**:
- `natomada.auth.events` - Eventos de autenticação
- `natomada.vehicles.events` - Eventos de veículos
- `natomada.stations.events` - Eventos de estações
- `natomada.dlq` - Dead Letter Queue (mensagens com erro)

---

## 📊 Logs da Aplicação

### **Logback** - Sistema de Logs

O Logback **não tem interface web própria**, mas você pode visualizar os logs de várias formas:

#### Opção 1: Docker Logs (Mais Simples)

```bash
# Ver logs em tempo real
docker compose logs app -f

# Ver logs de eventos Kafka
docker compose logs app -f | grep -E "(Publishing|Processing|EVENT)"

# Ver últimas 100 linhas
docker compose logs app --tail 100

# Ver logs de um serviço específico
docker compose logs postgres -f
```

#### Opção 2: Arquivos de Log

Os logs são salvos em arquivos rotativos:

```bash
# Acessar container
docker compose exec app sh

# Ver logs da aplicação
cat /tmp/natomada.log

# Ver logs de eventos Kafka
cat /tmp/kafka-events.log
```

#### Opção 3: Ferramentas de Agregação (Opcionais)

Para visualização web dos logs, você pode adicionar:

##### **Grafana + Loki** (Recomendado)
- Interface moderna
- Pesquisa poderosa
- Dashboards customizáveis
- Alertas em tempo real

##### **Elasticsearch + Kibana**
- Mais robusto para grandes volumes
- Analytics avançados
- Machine learning

##### **Graylog**
- Open source
- Fácil de configurar
- Bom para troubleshooting

---

## 🔧 Alterar Configurações

### Personalizar Credenciais e Portas

Crie um arquivo `.env` na raiz do projeto:

```env
# pgAdmin
PGADMIN_EMAIL=seu-email@example.com
PGADMIN_PASSWORD=sua-senha-segura
PGADMIN_PORT=5050

# PostgreSQL
POSTGRES_DB=natomada
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres
POSTGRES_PORT=5432

# Redis
REDIS_PASSWORD=redis
REDIS_PORT=6379

# Application
APP_PORT=8080
LOG_LEVEL=INFO
LOG_LEVEL_APP=DEBUG

# Kafka
KAFKA_CONSUMER_GROUP=natomada-group
```

Depois de criar o `.env`:

```bash
docker compose down
docker compose up -d
```

---

## 🚀 Resumo dos Serviços

| Serviço | URL | Credenciais | Finalidade |
|---------|-----|-------------|------------|
| **App** | http://localhost:8080 | JWT Token | API REST |
| **Grafana** | http://localhost:3001 | admin / admin | **Visualizar Logs** ⭐ |
| **pgAdmin** | http://localhost:5050 | admin@natomada.com / admin | Gerenciar PostgreSQL |
| **Kafka UI** | http://localhost:8090 | Sem autenticação | Monitorar eventos |
| **PostgreSQL** | localhost:5432 | postgres / postgres | Banco de dados |
| **Redis** | localhost:6379 | redis | Cache/Sessions |
| **Loki** | localhost:3100 | - | Agregação de logs |

---

## 🐛 Troubleshooting

### pgAdmin não abre

```bash
# Verificar se está rodando
docker compose ps pgadmin

# Ver logs de erro
docker compose logs pgadmin

# Reiniciar
docker compose restart pgadmin
```

### Kafka UI não mostra tópicos

```bash
# Verificar se Kafka está saudável
docker compose ps kafka

# Reiniciar Kafka
docker compose restart kafka kafka-ui
```

### Logs não aparecem

```bash
# Verificar se app está rodando
docker compose ps app

# Ver logs de startup
docker compose logs app --tail 200
```

---

## 📚 Próximos Passos

1. ✅ **Visualizar Logs** no Grafana + Loki
2. ✅ **Explorar o Banco de Dados** no pgAdmin
3. ✅ **Monitorar Eventos** no Kafka UI
4. ⏳ **Criar Dashboards** no Grafana (métricas, gráficos)
5. ⏳ **Configurar Alertas** no Grafana (erros críticos, falhas)
6. ⏳ **Adicionar Prometheus** (métricas da aplicação)

**Stack completo de observabilidade instalado!** 🎉
