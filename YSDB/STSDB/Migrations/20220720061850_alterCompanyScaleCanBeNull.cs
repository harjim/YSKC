using Microsoft.EntityFrameworkCore.Migrations;

namespace STSDB.Migrations
{
    public partial class alterCompanyScaleCanBeNull : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<int>(
                name: "scale",
                table: "company",
                nullable: true,
                oldClrType: typeof(int));
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<int>(
                name: "scale",
                table: "company",
                nullable: false,
                oldClrType: typeof(int),
                oldNullable: true);
        }
    }
}
