using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class addPatent_planRemarkAndMasterIdCols : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "masterId",
                table: "patent_plan",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "remark",
                table: "patent_plan",
                maxLength: 200,
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "masterId",
                table: "patent_plan");

            migrationBuilder.DropColumn(
                name: "remark",
                table: "patent_plan");
        }
    }
}
