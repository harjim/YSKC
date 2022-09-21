using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class addPatent_planConfirmNameAndTypeCols : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "confirmName",
                table: "patent_plan",
                maxLength: 200,
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "confirmType",
                table: "patent_plan",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "confirmName",
                table: "patent_plan");

            migrationBuilder.DropColumn(
                name: "confirmType",
                table: "patent_plan");
        }
    }
}
