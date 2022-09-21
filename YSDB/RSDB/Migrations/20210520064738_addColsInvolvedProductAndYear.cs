using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addColsInvolvedProductAndYear : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "involvedProduct",
                table: "p_project",
                maxLength: 50,
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "year",
                table: "p_docFile_footer",
                nullable: false,
                defaultValue: 0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "involvedProduct",
                table: "p_project");

            migrationBuilder.DropColumn(
                name: "year",
                table: "p_docFile_footer");
        }
    }
}
