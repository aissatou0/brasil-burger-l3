# -------- BUILD STAGE --------
FROM mcr.microsoft.com/dotnet/sdk:8.0 AS build
WORKDIR /src

# Copier uniquement le dossier Csharp
COPY Csharp ./Csharp

# Se placer dans le bon dossier
WORKDIR /src/Csharp/BrasilBurger.Web

RUN dotnet restore BrasilBurger.Web.csproj
RUN dotnet publish BrasilBurger.Web.csproj -c Release -o /app/out

# -------- RUNTIME STAGE --------
FROM mcr.microsoft.com/dotnet/aspnet:8.0 AS runtime
WORKDIR /app

COPY --from=build /app/out .

ENV ASPNETCORE_URLS=http://0.0.0.0:$PORT
EXPOSE 8080

ENTRYPOINT ["dotnet", "BrasilBurger.Web.dll"]
