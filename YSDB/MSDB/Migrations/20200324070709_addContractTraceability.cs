using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class addContractTraceability : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropPrimaryKey(
                name: "PK_contractTraceabilities",
                table: "contractTraceabilities");

            migrationBuilder.RenameTable(
                name: "contractTraceabilities",
                newName: "contractTraceability");

            migrationBuilder.AddPrimaryKey(
                name: "PK_contractTraceability",
                table: "contractTraceability",
                column: "id");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropPrimaryKey(
                name: "PK_contractTraceability",
                table: "contractTraceability");

            migrationBuilder.RenameTable(
                name: "contractTraceability",
                newName: "contractTraceabilities");

            migrationBuilder.AddPrimaryKey(
                name: "PK_contractTraceabilities",
                table: "contractTraceabilities",
                column: "id");
        }
    }
}
