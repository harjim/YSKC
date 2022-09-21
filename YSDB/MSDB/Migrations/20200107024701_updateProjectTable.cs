using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class updateProjectTable : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "artisanStatus",
                table: "project");

            migrationBuilder.RenameColumn(
                name: "FinanceStatus",
                table: "project",
                newName: "financeStatus");

            migrationBuilder.RenameColumn(
                name: "describe",
                table: "project",
                newName: "description");

            migrationBuilder.AlterColumn<int>(
                name: "financeStatus",
                table: "project",
                nullable: false,
                oldClrType: typeof(bool));

            migrationBuilder.AddColumn<int>(
                name: "techStatus",
                table: "project",
                nullable: false,
                defaultValue: 0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "techStatus",
                table: "project");

            migrationBuilder.RenameColumn(
                name: "financeStatus",
                table: "project",
                newName: "FinanceStatus");

            migrationBuilder.RenameColumn(
                name: "description",
                table: "project",
                newName: "describe");

            migrationBuilder.AlterColumn<bool>(
                name: "FinanceStatus",
                table: "project",
                nullable: false,
                oldClrType: typeof(int));

            migrationBuilder.AddColumn<bool>(
                name: "artisanStatus",
                table: "project",
                nullable: false,
                defaultValue: false);
        }
    }
}
