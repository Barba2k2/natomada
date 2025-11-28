# 🎯 Grafana + Loki - Guia Rápido

## ⚡ Acesso Rápido

**URL**: http://localhost:3001
**Login**: `admin`
**Senha**: `admin`

---

## 🚀 Primeiros Passos (2 minutos)

### 1. Acesse o Grafana

Abra: http://localhost:3001

### 2. Faça Login

- Usuário: `admin`
- Senha: `admin`
- Clique em **"Log in"**

(Na primeira vez, pode pedir para trocar a senha - você pode pular clicando em "Skip")

### 3. Abra o Explore

- No menu lateral esquerdo, clique no ícone de **bússola** 🧭 (Explore)
- No topo, verifique se **"Loki"** está selecionado como datasource

### 4. Sua Primeira Query

Cole isso no campo de query:

```logql
{container="natomada-app"}
```

Clique em **"Run query"** (botão azul no canto superior direito)

🎉 **Pronto!** Você está vendo os logs da sua aplicação em tempo real!

---

## 📊 Queries Essenciais (Copy & Paste)

### Ver Logs em Tempo Real

```logql
{container="natomada-app"}
```

Depois clique no botão **"Live"** no canto superior direito para ativar o tail ao vivo!

### Ver Apenas Erros

```logql
{container="natomada-app"} |= "ERROR"
```

### Ver Eventos Kafka Sendo Publicados

```logql
{container="natomada-app"} |= "Publishing event"
```

### Ver Eventos Kafka Sendo Consumidos

```logql
{container="natomada-app"} |= "Processing USER_REGISTERED"
```

### Ver Logs de Registro de Usuário

```logql
{container="natomada-app"} |= "User registered successfully"
```

### Ver Logs de Login

```logql
{container="natomada-app"} |= "User logged in successfully"
```

### Ver Todos os Logs do Sistema

```logql
{service=~"app|postgres|kafka"}
```

### Ver Logs de Queries SQL

```logql
{container="natomada-app"} |= "select"
```

---

## 🎯 Recursos Úteis

### 📌 Live Tail (Logs em Tempo Real)

1. Faça uma query
2. Clique no botão **"Live"** (canto superior direito)
3. Veja os logs aparecerem em tempo real!

### 🔍 Ver Contexto de um Log

1. Clique em qualquer linha de log
2. Clique em **"Show context"**
3. Veja o que aconteceu antes e depois daquele log

### 📅 Filtrar por Período

- No canto superior direito, clique no seletor de tempo
- Escolha: Last 5 minutes, Last 1 hour, Last 24 hours, etc.
- Ou selecione um período customizado

### 💾 Exportar Logs

1. Faça sua query
2. Clique no botão **"Inspector"** (canto superior direito)
3. Aba **"Data"**
4. Clique em **"Download logs"**

---

## 🎨 Dicas de Uso

### Operadores Úteis

- `|=` - Contém o texto (case-sensitive)
- `!=` - Não contém o texto
- `|~ "regex"` - Regex match
- `!~ "regex"` - Regex não match

### Exemplos

**Ver logs sem INFO**:
```logql
{container="natomada-app"} != "INFO"
```

**Ver logs de Kafka (producer OU consumer)**:
```logql
{container="natomada-app"} |~ "Publishing|Processing"
```

**Ver apenas SQL INSERTs**:
```logql
{container="natomada-app"} |= "insert"
```

---

## 🔥 Cenários Práticos

### Debugar um Erro

1. Query:
   ```logql
   {container="natomada-app"} |= "ERROR"
   ```
2. Clique no log de erro
3. "Show context" para ver o contexto completo
4. Veja o stack trace

### Acompanhar um Registro de Usuário

1. Faça um registro via API
2. No Grafana, use:
   ```logql
   {container="natomada-app"} |= "User registered successfully"
   ```
3. Veja o log aparecer em tempo real!

### Ver Todo o Fluxo de um Evento Kafka

```logql
{container="natomada-app"} |~ "Publishing event|Event published|Processing"
```

Você verá:
1. "Publishing event..." - Publicando
2. "Event published successfully..." - Publicado
3. "Processing USER_REGISTERED..." - Consumido

---

## 🎯 Próximos Passos

### Criar um Dashboard

1. Menu lateral → **"Dashboards"**
2. **"New"** → **"New Dashboard"**
3. **"Add visualization"**
4. Selecione **"Loki"**
5. Cole sua query favorita
6. **"Save dashboard"**

### Configurar Alertas

1. Crie um painel no dashboard
2. Clique em **"Alert"**
3. Configure a condição (ex: mais de 5 erros em 1 minuto)
4. Configure notificação (Slack, Email, etc.)

---

## 📚 Documentação

- **LogQL Syntax**: https://grafana.com/docs/loki/latest/logql/
- **Grafana Docs**: https://grafana.com/docs/grafana/latest/

---

## 🐛 Troubleshooting

### Não vejo logs

```bash
# Verificar se Promtail está rodando
docker compose ps promtail

# Ver logs do Promtail
docker compose logs promtail

# Reiniciar
docker compose restart promtail
```

### Loki não conecta

```bash
# Verificar se Loki está rodando
docker compose ps loki

# Ver logs
docker compose logs loki

# Reiniciar
docker compose restart loki grafana
```

---

**Tudo pronto! Explore seus logs! 🚀**
