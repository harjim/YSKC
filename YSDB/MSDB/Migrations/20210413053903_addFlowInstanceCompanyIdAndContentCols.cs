using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class addFlowInstanceCompanyIdAndContentCols : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "companyId",
                table: "flowInstance",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "content",
                table: "flowInstance",
                maxLength: 200,
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "companyId",
                table: "flowInstance");

            migrationBuilder.DropColumn(
                name: "content",
                table: "flowInstance");
        }
    }
}
