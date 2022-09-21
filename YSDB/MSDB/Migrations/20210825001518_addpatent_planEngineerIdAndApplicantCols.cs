using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class addpatent_planEngineerIdAndApplicantCols : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "masterId",
                table: "patent_plan");

            migrationBuilder.AddColumn<string>(
                name: "applicant",
                table: "patent_plan",
                maxLength: 200,
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "engineerId",
                table: "patent_plan",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "engineerId",
                table: "patent_buying_demand",
                nullable: false,
                defaultValue: 0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "applicant",
                table: "patent_plan");

            migrationBuilder.DropColumn(
                name: "engineerId",
                table: "patent_plan");

            migrationBuilder.DropColumn(
                name: "engineerId",
                table: "patent_buying_demand");

            migrationBuilder.AddColumn<int>(
                name: "masterId",
                table: "patent_plan",
                nullable: true);
        }
    }
}
